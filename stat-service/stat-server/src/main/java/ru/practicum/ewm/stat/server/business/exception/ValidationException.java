package ru.practicum.ewm.stat.server.business.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
