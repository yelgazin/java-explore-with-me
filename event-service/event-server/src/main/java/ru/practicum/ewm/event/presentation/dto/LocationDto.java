package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    @NotNull(message = "Широта не может быть пустым значением.")
    Float lat;

    @NotNull(message = "Долгота не может быть пустым значением.")
    Float lon;
}