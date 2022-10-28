package enit.rhaddad.api.dto;

import java.util.List;

public record PlaceOrderCommand(String customer,List<PlaceOrderItemDTO> items) {
    
}
