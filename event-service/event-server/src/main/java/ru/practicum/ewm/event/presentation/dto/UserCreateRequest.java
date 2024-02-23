package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    static final String NAME_BLANK_MESSAGE = "Имя пользовтаеля должно содержать печатные символы.";
    static final String NAME_LENGTH_MESSAGE = "Имя пользовтаеля должно содержать от 2 до 250 символов.";
    static final String EMAIL_LENGTH_MESSAGE = "Почтовый адрес должен содержать от 6 до 254 печатных символов.";
    static final String EMAIL_FORMAT_MESSAGE = "Электронная почта не соответствует формату \"user@mail.ru\".";

    @NotBlank(message = NAME_BLANK_MESSAGE)
    @Size(min = 2, max = 250, message = NAME_LENGTH_MESSAGE)
    String name;

    @NotBlank(message = EMAIL_LENGTH_MESSAGE)
    @Size(min = 6, max = 254, message = EMAIL_LENGTH_MESSAGE)
    @Email(message = EMAIL_FORMAT_MESSAGE)
    String email;
}
