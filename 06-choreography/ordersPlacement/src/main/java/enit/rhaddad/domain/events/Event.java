package enit.rhaddad.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
 use = JsonTypeInfo.Id.NAME,
 include = JsonTypeInfo.As.EXISTING_PROPERTY,
 property = "eventType",
 visible = true
)
@JsonSubTypes({
 @JsonSubTypes.Type(value = OrderPlaced.class, name = "OrderPlaced")
})

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "eventId")
public abstract class Event {
    protected UUID eventId = UUID.randomUUID();
    protected String eventType;
    protected String aggregateType;
    protected String aggregateId;
    protected LocalDateTime createdAt = LocalDateTime.now();
    public Event(String eventType, String aggregateType, String aggregateId) {
        this.eventType = eventType;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
    }
    
}
