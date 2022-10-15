package enit.rhaddad.api.dto;


import enit.rhaddad.domain.CoffeeType;

public record OrderItemViewDTO(CoffeeType type,int quantity) {
    
    
}
