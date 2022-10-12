package enit.rhaddad.api.dto;

import java.util.List;

public record CreateOrderCommand(String customer,List<CreateOrderItemDTO> items) {
    
}
