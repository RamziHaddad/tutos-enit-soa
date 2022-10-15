package enit.rhaddad.api.dto;

import java.util.List;
import java.util.UUID;

public record MakeOrderCommand (UUID orderId,String customer,List<OrderItemViewDTO> items){

}
