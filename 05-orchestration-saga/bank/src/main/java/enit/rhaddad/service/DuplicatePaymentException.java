package enit.rhaddad.service;

import java.util.UUID;

public class DuplicatePaymentException extends Exception{

    public DuplicatePaymentException(UUID id) {
        super("Transaction "+id+" already exists");
    }
    
}
