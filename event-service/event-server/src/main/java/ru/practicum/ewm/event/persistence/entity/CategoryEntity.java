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
@Table(name = "category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity extends AbstractEntity {

    String name;
}
