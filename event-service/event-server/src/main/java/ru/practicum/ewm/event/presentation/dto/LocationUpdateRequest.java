package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.presentation.validation.NullOrNotBlank;

import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationUpdateRequest {

    static final String NAME_NULL_OR_BLANK_MESSAGE
            = "Название локации должно содержать печатные символы или отсутствовать.";
    static final String NAME_LENGTH_MESSAGE
            = "Название локации должно содержать от 1 до 50 символов.";

    static final String POLYGON_NULL_OR_BLANK_MESSAGE =
            "Полигон должен содержать сведения или отсутствовать.";

    @NullOrNotBlank(message = NAME_NULL_OR_BLANK_MESSAGE)
    @Size(min = 1, max = 50, message = NAME_LENGTH_MESSAGE)
    String name;

    @NullOrNotBlank(message = POLYGON_NULL_OR_BLANK_MESSAGE)
    String polygon;
}
