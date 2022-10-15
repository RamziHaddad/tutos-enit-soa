package enit.rhaddad.api.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(UUID orderId, String customer,List<CreateOrderItemDTO> items) {
    
}
