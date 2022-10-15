package enit.rhaddad.api.dto;


import enit.rhaddad.domain.CoffeeType;

public record MakeOrderItem(CoffeeType coffeeType,int quantity) {
    
    
}
