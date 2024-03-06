package ru.practicum.ewm.event.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "participation_request")
public class ParticipationRequestEntity extends AbstractEntity {

    public enum ParticipationRequestState {
        PENDING, CONFIRMED, REJECTED, CANCELED
    }

    @ManyToOne
    EventEntity event;

    @ManyToOne
    UserEntity requester;

    @Enumerated(EnumType.STRING)
    ParticipationRequestState state;

    @CreationTimestamp
    LocalDateTime created;
}
