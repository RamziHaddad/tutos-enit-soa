package enit.rhaddad.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.transaction.Transactional;

import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.OrderItem;
import enit.rhaddad.repository.OrderRepository;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;

@ApplicationScoped
public class BaristaService {
    
    @Inject
    OrderRepository repo;

    @Transactional
    public void receiveNewOrder(Order order){
        repo.persistOrder(order);
    }
    @Transactional
    public List<Order> getAllOrders(){
        return repo.queryallOrders();
    }
    public Optional<Order> getOrderById(UUID id){
        return repo.queryOrderById(id);
    }
    @Scheduled(every="10s",concurrentExecution = ConcurrentExecution.SKIP)
    public void makeCoffees(){
        Optional<Order> o = repo.queryFirstNonReadyOrder();
        o.ifPresent(order->{
            for(OrderItem item:order.getItems()){
                while(!item.isReady()){
                    try {
                        Thread.sleep(1000);
                        order.incrementReady(item.getCoffeeType());
                        repo.updateOrder(order);
                    } catch (Exception e) {
                        QuarkusTransaction.rollback();
                        e.printStackTrace();
                    }    
                }
            }
        });
    }
}
