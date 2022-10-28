package enit.rhaddad.repository;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.OrderItem;
import enit.rhaddad.domain.events.OrderPlaced;
import enit.rhaddad.service.BaristaService;

@ApplicationScoped
public class InboxService {
    

    @Inject
    BaristaService barista;

    @Incoming("placed-orders")
    @Transactional
    public void newOrder(OrderPlaced event){
        Order order = new Order(UUID.fromString(event.getAggregateId()),event.getCustomer());
        List<OrderItem> items = event.getItems().entrySet().stream().map(e->new OrderItem(e.getKey(),e.getValue().intValue())).toList();
        order.addItems(items);
        barista.receiveNewOrder(order);
    }
}
