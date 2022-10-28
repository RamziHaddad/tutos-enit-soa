package enit.rhaddad.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import enit.rhaddad.domain.events.Event;
import enit.rhaddad.repository.OutboxRepository;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;

@ApplicationScoped
public class OutboxService {
    
    @Inject
    OutboxRepository repo;
    @Inject
    @Channel("placed-orders")
    Emitter<Event> eventsPublisher;

    @Transactional
    public void publish(Event e){
        repo.persist(e);
        //can publish here to avoid lag but should consider out of order events (e.g. receive order cancellation before reception).
    }

    @Scheduled(every = "10s",concurrentExecution = ConcurrentExecution.SKIP)
    public void emitEvents(){
        List<Event> events = repo.queryNextWaitingOutboxEvent();
        events.stream().forEach(e->{
            eventsPublisher.send(e);
            repo.markAsSent(e.getEventId());
        });
    }

}
