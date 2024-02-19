package ru.practicum.ewm.stat.server.persistence.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "hit")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitEntity extends AbstractEntity {

    @ManyToOne
    EndpointEntity endpoint;

    String ip;

    LocalDateTime timestamp;
}
