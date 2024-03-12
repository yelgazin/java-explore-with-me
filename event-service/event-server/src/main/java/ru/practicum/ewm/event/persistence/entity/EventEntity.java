package ru.practicum.ewm.event.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "event")
public class EventEntity extends AbstractEntity {

    public enum EventState {
        PENDING, PUBLISHED, CANCELED
    }

    String title;
    String annotation;
    String description;
    LocalDateTime date;

    @ManyToOne
    @ToString.Exclude
    CategoryEntity category;

    @ManyToOne
    @ToString.Exclude
    UserEntity initiator;

    Point<G2D> location;

    boolean paid;
    Integer participantLimit;
    boolean requestModeration;
    Integer confirmedRequests;
    Long views;

    @Enumerated(EnumType.STRING)
    EventState state;

    @CreationTimestamp
    LocalDateTime created;

    LocalDateTime published;

    @ToString.Exclude
    @OneToMany(mappedBy = "event")
    Set<ParticipationRequestEntity> participationRequests;
}
