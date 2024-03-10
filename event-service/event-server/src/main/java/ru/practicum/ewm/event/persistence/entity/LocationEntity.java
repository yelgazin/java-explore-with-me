package ru.practicum.ewm.event.persistence.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "location")
public class LocationEntity extends AbstractEntity {

    String title;

    Polygon<G2D> polygon;
}
