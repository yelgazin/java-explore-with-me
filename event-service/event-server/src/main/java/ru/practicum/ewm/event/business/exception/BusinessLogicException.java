package ru.practicum.ewm.event.business.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessLogicException extends RuntimeException {

    String reason;

    public BusinessLogicException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public BusinessLogicException(String message) {
        super(message);
    }
}
