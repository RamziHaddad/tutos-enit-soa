package enit.rhaddad.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import enit.rhaddad.domain.Payment;

@ApplicationScoped
@Transactional
public class PaymentRepository {
    @Inject
    EntityManager em;

    
    public void persistPayment(Payment p){
        em.persist(p);
    }
    
    public List<Payment> queryallPayments(){
        return em.createQuery("from Payment p ",Payment.class).getResultList();
    }
   
    public Optional<Payment> queryPaymentById(UUID id){
        Payment o = em.find(Payment.class,id);
        return Optional.ofNullable(o);
    }
}
