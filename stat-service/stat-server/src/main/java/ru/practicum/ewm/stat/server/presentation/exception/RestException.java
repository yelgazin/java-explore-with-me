package ru.practicum.ewm.stat.server.presentation.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestException {

    String error;
    HttpStatus httpStatus;
}