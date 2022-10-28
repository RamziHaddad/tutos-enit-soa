package enit.rhaddad.api.dto;


import enit.rhaddad.domain.CoffeeType;

public record PlaceOrderItemDTO (CoffeeType coffeeType,int quantity) {
    
}