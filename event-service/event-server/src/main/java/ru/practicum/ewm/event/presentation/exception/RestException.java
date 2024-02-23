package ru.practicum.ewm.event.presentation.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestException {

    String message;
    String reason;
    List<String> errors;
    HttpStatus httpStatus;

    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
}