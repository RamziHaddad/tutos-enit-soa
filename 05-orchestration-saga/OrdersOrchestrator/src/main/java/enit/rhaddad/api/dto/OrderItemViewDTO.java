package enit.rhaddad.api.dto;


import enit.rhaddad.domain.CoffeeType;

public record OrderItemViewDTO(CoffeeType coffeeType,int quantity) {
    
    
}
