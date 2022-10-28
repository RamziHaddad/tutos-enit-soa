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
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class OrderPlaced extends BaseEvent{
    Map<CoffeeType,Long> items;
    BigDecimal price;
    String customer;

    public OrderPlaced(Order order) {
        super("OrderPlaced","Order", order.getId().toString());
        items = order.getItems().stream().collect(Collectors.groupingBy(OrderItem::getCoffeeType, Collectors.counting()));
        price = order.getPrice();
        customer = order.getCustomer();

    }
}