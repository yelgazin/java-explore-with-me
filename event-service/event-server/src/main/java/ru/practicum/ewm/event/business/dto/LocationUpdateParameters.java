package ru.practicum.ewm.event.business.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationUpdateParameters {

    String name;
    Polygon<G2D> polygon;
}
