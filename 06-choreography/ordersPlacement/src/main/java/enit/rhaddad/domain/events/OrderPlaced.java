package enit.rhaddad.domain.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
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
@EqualsAndHashCode(of = "eventId")
public class OrderPlaced implements Event{
    private UUID eventId = UUID.randomUUID();
    private String eventType;
    private String aggregateType;
    private String aggregateId;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private Map<CoffeeType,Long> items;
    private BigDecimal price;
    private String customer;

    public OrderPlaced(Order order) {
        this.eventType="OrderPlaced"; 
        this.aggregateType ="Order";
        this.aggregateId = order.getId().toString();
        this.items = order.getItems().stream().collect(Collectors.groupingBy(OrderItem::getCoffeeType, Collectors.counting()));
        this.price = order.getPrice();
        this.customer = order.getCustomer();

    }
}