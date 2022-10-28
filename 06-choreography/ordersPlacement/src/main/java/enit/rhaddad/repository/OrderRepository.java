package enit.rhaddad.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import enit.rhaddad.domain.Order;

@ApplicationScoped
@Transactional
public class OrderRepository {
    @Inject
    EntityManager em;


    public void persistOrder(Order order){
        em.persist(order);
    }
    
    public List<Order> queryallOrders(){
        return em.createQuery("from Order o order by o.receivedAt",Order.class).getResultList();
    }

    public Optional<Order> queryOrderById(UUID id){
        Order o = em.find(Order.class,id);
        return Optional.ofNullable(o);
    }
    
   
    
    
}
