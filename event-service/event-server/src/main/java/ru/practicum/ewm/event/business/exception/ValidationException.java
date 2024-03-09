package ru.practicum.ewm.event.business.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationException extends RuntimeException {

    final String reason;

    public ValidationException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
