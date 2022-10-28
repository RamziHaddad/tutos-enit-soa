package enit.rhaddad.repository;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import enit.rhaddad.domain.events.BaseEvent;

@ApplicationScoped
public class OutboxRepository {
    EntityManager em;
    private ObjectMapper jsonMapper;


    
    public OutboxRepository(ObjectMapper jsonMapper,EntityManager em) {
        this.em=em;
        this.jsonMapper = jsonMapper;
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType("enit.rhaddad.domain.events")
            .allowIfSubType("java.math")
            .allowIfSubType("java.util")
            .build();
        jsonMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
    }



    @Transactional
    public void persist(BaseEvent ev){
        String payload;
        try {
            payload = jsonMapper.writeValueAsString(ev);
            OutboxEvent oe = new OutboxEvent(ev.getEventId(),ev.getEventType() , ev.getCreatedAt(), ev.getAggregateId(), ev.getAggregateType(), payload,false);
            em.persist(oe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<BaseEvent> queryNextWaitingOutboxEvent() {
        List<OutboxEvent> oevents = em.createQuery("from OutboxEvent o where o.sent=false order by o.createdAt",OutboxEvent.class).getResultList();
        return oevents.stream().map(oe->{
            try {
                return jsonMapper.readValue(oe.getPayload(), BaseEvent.class);
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
