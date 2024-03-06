package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationCreateRequest {

    static final String TITLE_BLANK_MESSAGE = "Заголовок подборки должен содержать печатные символы.";
    static final String TITLE_LENGTH_MESSAGE = "Заголовок подборки должен содержать от 1 до 50 символов.";

    @NotBlank(message = TITLE_BLANK_MESSAGE)
    @Size(min = 1, max = 50, message = TITLE_LENGTH_MESSAGE)
    String title;

    Boolean pinned;
    Set<Long> events;
}
