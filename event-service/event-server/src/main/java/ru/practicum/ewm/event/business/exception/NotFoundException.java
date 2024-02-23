package ru.practicum.ewm.event.business.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundException extends RuntimeException {

    final String reason;

    public NotFoundException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}
