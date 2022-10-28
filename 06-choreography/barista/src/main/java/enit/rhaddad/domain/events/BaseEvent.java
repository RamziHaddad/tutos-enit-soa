package enit.rhaddad.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "eventId")
public abstract class BaseEvent {
    protected UUID eventId = UUID.randomUUID();
    protected String eventType;
    protected String aggregateType;
    protected String aggregateId;
    protected LocalDateTime createdAt = LocalDateTime.now();
    public BaseEvent(String eventType, String aggregateType, String aggregateId) {
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
    }
    

}
