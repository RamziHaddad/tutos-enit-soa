package enit.rhaddad.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "eventId")
public class OutboxEvent {
    private @Id UUID eventId;
    private String eventType;
    private LocalDateTime createdAt;
    private String aggregateId;
    private String aggregateType;
    @Column(columnDefinition="TEXT")
    private String payload;
    private boolean sent;

}
