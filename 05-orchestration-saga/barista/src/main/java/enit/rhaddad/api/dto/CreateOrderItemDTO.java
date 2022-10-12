package enit.rhaddad.api.dto;

import enit.rhaddad.domain.CoffeeType;

public record CreateOrderItemDTO (CoffeeType coffeeType,int quantity) {
    
}