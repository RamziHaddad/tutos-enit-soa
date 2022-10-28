package enit.rhaddad.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import enit.rhaddad.domain.events.BaseEvent;
import enit.rhaddad.repository.OutboxRepository;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;

@ApplicationScoped
public class OutboxService {
    
    @Inject
    OutboxRepository repo;
    @Inject
    @Channel("orders")
    Emitter<BaseEvent> eventsPublisher;

    @Transactional
    public void publish(BaseEvent e){
        repo.persist(e);
        //can publish here to avoid lag but should consider out of order events (e.g. receive order cancellation before reception).
    }

    @Scheduled(every = "30s", delay = 60,delayUnit = TimeUnit.SECONDS,concurrentExecution = ConcurrentExecution.SKIP)
    public void emitEvents(){
        List<BaseEvent> events = repo.queryNextWaitingOutboxEvent();
        events.stream().forEach(e->{
            eventsPublisher.send(e);
            repo.markAsSent(e.getEventId());
        });
    }

}
