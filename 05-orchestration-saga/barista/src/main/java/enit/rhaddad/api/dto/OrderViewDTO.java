package enit.rhaddad.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.OrderStatus;

public record OrderViewDTO(UUID id,String customer,List<OrderItemViewDTO> items,LocalDateTime receivedAt, OrderStatus status) {
    public OrderViewDTO(Order order){
        this(order.getId(),order.getCustomer(),order.getItems().stream().map(i->new OrderItemViewDTO(i.getCoffeeType(), i.getQuantity(),i.getQuantityReady())).toList(),order.getReceivedAt(),order.getStatus());
    }
}