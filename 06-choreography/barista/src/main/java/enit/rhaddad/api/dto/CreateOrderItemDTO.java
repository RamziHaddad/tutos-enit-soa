package enit.rhaddad.api.dto;

import javax.validation.constraints.Positive;

import enit.rhaddad.domain.CoffeeType;

public record CreateOrderItemDTO (CoffeeType coffeeType,@Positive int quantity) {
    
}