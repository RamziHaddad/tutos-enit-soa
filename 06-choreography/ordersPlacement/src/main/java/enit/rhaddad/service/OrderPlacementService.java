package enit.rhaddad.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.events.OrderPlaced;
import enit.rhaddad.repository.OrderRepository;


@ApplicationScoped
public class OrderPlacementService {
    
    @Inject
    OrderRepository repo;

    @Inject
    OutboxService outbox;

    @Inject
    PricingService pricingService;

    @Transactional
    public void placeNewOrder(Order order){
        Optional<BigDecimal> price = order.getItems().stream().map(i->pricingService.getCoffeePrice(i.getCoffeeType())).reduce((a, b) -> a.add(b));
        if(price.isEmpty()){
            throw new RuntimeException("couldn't determine price");
        }else{
            order.setPrice(price.get());
        }
        repo.persistOrder(order);
        outbox.publish(new OrderPlaced(order));
    }

    @Transactional
    public List<Order> getAllOrders(){
        return repo.queryallOrders();
    }

    @Transactional
    public Optional<Order> getOrderById(UUID id){
        return repo.queryOrderById(id);
    }





}
