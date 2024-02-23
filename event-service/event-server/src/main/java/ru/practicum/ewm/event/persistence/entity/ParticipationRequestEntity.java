package ru.practicum.ewm.event.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "participation_request")
public class ParticipationRequestEntity extends AbstractEntity {

    public enum ParticipationRequestState {
        PENDING, PUBLISHED, CANCELED;
    }

    @ManyToOne
    EventEntity event;

    @ManyToOne
    UserEntity requester;

    @Enumerated(EnumType.STRING)
    ParticipationRequestState state;
}
