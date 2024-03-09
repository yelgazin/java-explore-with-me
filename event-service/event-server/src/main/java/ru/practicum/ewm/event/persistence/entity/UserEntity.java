package ru.practicum.ewm.event.persistence.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity {

    String email;
    String name;
}
