package ru.practicum.ewm.event.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.FORMAT_DATE_TIME;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreateRequest {

    static final String TITLE_BLANK_MESSAGE = "Заголовок события должен содержать печатные символы.";
    static final String TITLE_LENGTH_MESSAGE = "Заголовок события должен содержать от 3 до 120 символов.";

    static final String ANNOTATION_BLANK_MESSAGE = "Краткое описание события должно содержать печатные символы.";
    static final String ANNOTATION_LENGTH_MESSAGE
            = "Краткое описание события должно содержать от 20 до 2000 символов.";

    static final String DESCRIPTION_BLANK_MESSAGE = "Полное описание события должно содержать печатные символы.";
    static final String DESCRIPTION_LENGTH_MESSAGE
            = "Полное описание события должно содержать от 20 до 7000 символов.";

    @NotBlank(message = TITLE_BLANK_MESSAGE)
    @Size(min = 3, max = 120, message = TITLE_LENGTH_MESSAGE)
    String title;

    @NotBlank(message = ANNOTATION_BLANK_MESSAGE)
    @Size(min = 20, max = 2000, message = ANNOTATION_LENGTH_MESSAGE)
    String annotation;

    @NotBlank(message = DESCRIPTION_BLANK_MESSAGE)
    @Size(min = 20, max = 7000, message = DESCRIPTION_LENGTH_MESSAGE)
    String description;

    @NotNull(message = "Идентификатор категории не может быть пустым.")
    Long category;

    @JsonFormat(pattern = FORMAT_DATE_TIME)
    @NotNull(message = "Дата и время события не может быть пустым.")
    LocalDateTime eventDate;

    @Valid
    @NotNull(message = "Местоположение событие не может быть пустым.")
    PointDto location;

    Boolean paid;

    @PositiveOrZero(message = "Ограничение на количество участников не может принимать отрицательные значения.")
    Integer participantLimit;

    Boolean requestModeration;
}
