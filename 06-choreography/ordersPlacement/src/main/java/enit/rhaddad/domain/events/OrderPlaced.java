package enit.rhaddad.domain.events;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import enit.rhaddad.domain.CoffeeType;
import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.OrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * OrderPlaced
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderPlaced extends Event{    
    private Map<CoffeeType,Long> items;
    private BigDecimal price;
    private String customer;

    public OrderPlaced(Order order) {
        super("OrderPlaced","Order",order.getId().toString());
        this.items = order.getItems().stream().collect(Collectors.groupingBy(OrderItem::getCoffeeType, Collectors.counting()));
        this.price = order.getPrice();
        this.customer = order.getCustomer();

    }
}