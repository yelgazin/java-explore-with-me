package ru.practicum.ewm.event.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event")
public class EventEntity extends AbstractEntity {

    public enum EventState {
        PENDING, PUBLISHED, CANCELED;
    }

    String title;
    String annotation;
    String description;
    LocalDateTime date;

    @ManyToOne
    CategoryEntity category;

    @ManyToOne
    UserEntity initiator;

    @Embedded
    @AttributeOverride(name = "lat", column = @Column(name = "location_lat"))
    @AttributeOverride(name = "lon", column = @Column(name = "location_lon"))
    LocationEntity location;

    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    Long views;

    @Enumerated(EnumType.STRING)
    EventState state;

    @CreationTimestamp
    LocalDateTime created;

    LocalDateTime published;

    @Transient
    Integer confirmedRequests;

    @OneToMany(mappedBy = "event")
    Set<ParticipationRequestEntity> participationRequests;
}
