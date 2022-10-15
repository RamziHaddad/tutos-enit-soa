package enit.rhaddad.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.transaction.Transactional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import enit.rhaddad.api.dto.MakeOrderCommand;
import enit.rhaddad.api.dto.MakePaymentCommand;
import enit.rhaddad.api.dto.OrderItemViewDTO;
import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.OrderItem;
import enit.rhaddad.domain.OrderStatus;
import enit.rhaddad.repository.OrderRepository;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;

@ApplicationScoped
public class OrderService {
    
    @Inject
    OrderRepository repo;

    @Inject
    PricingService pricingService;

    @Inject
    @RestClient
    PaymentService paymentService;

    @Inject
    @RestClient
    BaristaService baristaService;

    @Transactional
    public void receiveNewOrder(Order order){
        Optional<BigDecimal> price = order.getItems().stream().map(i->pricingService.getCoffeePrice(i.getCoffeeType())).reduce((a, b) -> a.add(b));
        if(price.isEmpty()){
            throw new RuntimeException("couldn't determine price");
        }else{
            order.setPrice(price.get());
            repo.updateOrder(order);
        }
        repo.persistOrder(order);
    }

    @Transactional
    public void processPayment(Order order) {
        MakePaymentCommand cmd = new MakePaymentCommand(order.getId(), order.getPrice(), order.getCardNumber(),order.getCardCode());
        paymentService.makePayment(cmd);
        order.setStatus(OrderStatus.PAID);
        repo.updateOrder(order);
    }

    @Transactional
    public void dispatchOrderToBarista(Order order) {
        List<OrderItemViewDTO> items = order.getItems().stream().map(oi->new OrderItemViewDTO(oi.getCoffeeType(),oi.getQuantity())).toList();
        MakeOrderCommand cmd = new MakeOrderCommand(order.getId(), order.getCustomer(), items);
        baristaService.makeOrder(cmd);
        order.setStatus(OrderStatus.DISPATCHED);
        repo.updateOrder(order);
    }

    @Transactional
    public List<Order> getAllOrders(){
        return repo.queryallOrders();
    }

    @Transactional
    public Optional<Order> getOrderById(UUID id){
        return repo.queryOrderById(id);
    }

    @Scheduled(every="10s",concurrentExecution = ConcurrentExecution.SKIP)
    @Transactional
    public void resumeOrdersWaitingForPayment(){
        Optional<Order> o = repo.queryNextReceivedOrder();
        o.ifPresent(order->{
            try{
                processPayment(order);
                order.setStatus(OrderStatus.PAID);
            }catch(Exception e){
                order.setStatus(OrderStatus.PAIMENT_FAILED);
                repo.updateOrder(order);
            }
        });
    }
    @Scheduled(every="10s",concurrentExecution = ConcurrentExecution.SKIP)
    @Transactional
    public void resumeOrdersWaitingForPaymentSecondTry(){
        Optional<Order> o = repo.queryNextPaymentFailedOrder();
        o.ifPresent(order->{
            try{
                processPayment(order);
                order.setStatus(OrderStatus.PAID);
                repo.updateOrder(order);
            }catch(Exception e){
                order.setStatus(OrderStatus.CANCELLED);
                repo.updateOrder(order);
            }
        });
    }
    @Scheduled(every="10s",concurrentExecution = ConcurrentExecution.SKIP)
    @Transactional
    public void resumeOrdersWaitingForDispatchToBarista(){
        Optional<Order> o = repo.queryNextPaidOrder();
        o.ifPresent(order->{
            dispatchOrderToBarista(order);
            order.setStatus(OrderStatus.DISPATCHED);
            repo.updateOrder(order);
        });
    }
    @Scheduled(every="10s",concurrentExecution = ConcurrentExecution.SKIP)
    @Transactional
    public void checkDispatcheOrdersWaitingForBarista(){
        Optional<Order> o = repo.queryNextDispatchedOrder();
        o.ifPresent(order->{
            String status = baristaService.getOrderStatus(order.getId());
            if("READY".equals(status)){
                order.setStatus(OrderStatus.READY);
                repo.updateOrder(order);
            }
        });
    }




}
