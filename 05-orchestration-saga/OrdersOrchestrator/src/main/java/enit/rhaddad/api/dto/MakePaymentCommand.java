package enit.rhaddad.api.dto;

import java.math.BigDecimal;
import java.util.UUID;


    public record MakePaymentCommand(UUID id, BigDecimal price, int cardNumber, int cardCode) {
    }


