package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreateRequest {

    static final String NAME_BLANK_MESSAGE = "Название категории должно содержать печатные символы.";
    static final String NAME_LENGTH_MESSAGE = "Название категории должно содержать от 1 до 50 символов.";

    @NotBlank(message = NAME_BLANK_MESSAGE)
    @Size(min = 1, max = 50, message = NAME_LENGTH_MESSAGE)
    String name;
}
