package ru.practicum.ewm.event.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.presentation.validation.NullOrNotBlank;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.FORMAT_DATE_TIME;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequest {

    static final String TITLE_NULL_OR_BLANK_MESSAGE
            = "Заголовок события должен содержать печатные символы или отсутствовать.";
    static final String TITLE_LENGTH_MESSAGE
            = "Заголовок события должен содержать от 3 до 120 символов.";

    static final String ANNOTATION_NULL_OR_BLANK_MESSAGE
            = "Краткое описание события должно содержать печатные символы или отсутствовать.";
    static final String ANNOTATION_LENGTH_MESSAGE
            = "Краткое описание события должно содержать от 20 до 2000 символов.";

    static final String DESCRIPTION_NULL_OR_BLANK_MESSAGE
            = "Полное описание события должно содержать печатные символы или отсутствовать.";
    static final String DESCRIPTION_LENGTH_MESSAGE
            = "Полное описание события должно содержать от 20 до 7000 символов.";

    @NullOrNotBlank(message = TITLE_NULL_OR_BLANK_MESSAGE)
    @Size(min = 3, max = 120, message = TITLE_LENGTH_MESSAGE)
    String title;

    @NullOrNotBlank(message = ANNOTATION_NULL_OR_BLANK_MESSAGE)
    @Size(min = 20, max = 2000, message = ANNOTATION_LENGTH_MESSAGE)
    String annotation;

    @NullOrNotBlank(message = DESCRIPTION_NULL_OR_BLANK_MESSAGE)
    @Size(min = 20, max = 7000, message = DESCRIPTION_LENGTH_MESSAGE)
    String description;

    Long category;

    @JsonFormat(pattern = FORMAT_DATE_TIME)
    LocalDateTime eventDate;

    @Valid
    PointDto location;

    Boolean paid;

    @PositiveOrZero(message = "Ограничение на количество участников не может принимать отрицательные значения.")
    Integer participantLimit;

    Boolean requestModeration;

    EventStateAction stateAction;
}
