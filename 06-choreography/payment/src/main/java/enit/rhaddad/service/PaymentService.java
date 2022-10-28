package enit.rhaddad.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.transaction.Transactional;

import enit.rhaddad.domain.Payment;
import enit.rhaddad.repository.PaymentRepository;


@ApplicationScoped
public class PaymentService {
    
    @Inject
    PaymentRepository repo;

    @Transactional
    public void makeNewPayment(Payment p) throws DuplicatePaymentException{
        Optional<Payment> duplicate = repo.queryPaymentById(p.getTransactionId());
        if(duplicate.isEmpty()){
            repo.persistPayment(p);
        }else{
            throw new DuplicatePaymentException(p.getTransactionId());
        }
    }
    @Transactional
    public List<Payment> getAllPayments(){
        return repo.queryallPayments();
    }
    @Transactional
    public Optional<Payment> getPaymentById(UUID id){
        return repo.queryPaymentById(id);
    }
}
