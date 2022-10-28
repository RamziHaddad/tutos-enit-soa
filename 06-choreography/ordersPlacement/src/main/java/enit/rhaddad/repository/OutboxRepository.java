package enit.rhaddad.repository;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import enit.rhaddad.domain.events.Event;
import enit.rhaddad.domain.events.OrderPlaced;

@ApplicationScoped
public class OutboxRepository {
    @Inject
    EntityManager em;
    @Inject
    private ObjectMapper jsonMapper;


    @Transactional
    public void persist(Event ev){
        String payload;
        try {
            payload = jsonMapper.writeValueAsString(ev);
            OutboxEvent oe = new OutboxEvent(ev.getEventId(),ev.getEventType() , ev.getCreatedAt(), ev.getAggregateId(), ev.getAggregateType(), payload,false);
            em.persist(oe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<Event> queryNextWaitingOutboxEvent() {
        List<OutboxEvent> oevents = em.createQuery("from OutboxEvent o where o.sent=false order by o.createdAt",OutboxEvent.class).getResultList();
        return oevents.stream().map(oe->{
            try {
                return (Event) jsonMapper.readValue(oe.getPayload(), OrderPlaced.class);
            } catch (Exception e) {
               throw new RuntimeException(e.getMessage());
            }
        }).toList();
    }
    
    @Transactional
    public void markAsSent(UUID eventId){
        em.createQuery("update OutboxEvent oe set oe.sent=true where oe.eventId=:eid").setParameter("eid", eventId).executeUpdate();
    }
}
