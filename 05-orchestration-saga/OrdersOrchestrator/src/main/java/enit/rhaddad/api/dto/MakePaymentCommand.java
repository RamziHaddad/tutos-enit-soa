package enit.rhaddad.api.dto;

import java.math.BigDecimal;
import java.util.UUID;


    public record MakePaymentCommand(UUID transactionId, BigDecimal amount, int cardNumber, int cardCode) {
    }


