package ru.practicum.ewm.stat.server.persistence.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@Table(name = "endpoint")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointEntity extends AbstractEntity {

    String app;
    String uri;
}
