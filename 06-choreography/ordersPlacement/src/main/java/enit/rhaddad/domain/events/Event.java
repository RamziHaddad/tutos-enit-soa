package enit.rhaddad.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;


public interface Event {
    public UUID getEventId();
    public String getEventType();
    public String getAggregateType();
    public String getAggregateId();
    public LocalDateTime getCreatedAt();
}
