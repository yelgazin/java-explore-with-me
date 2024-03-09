package ru.practicum.ewm.stat.server.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.stat.server.business.exception.ValidationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConversionFailedException.class)
    public RestException handeConversionFailedException(ConversionFailedException ex) {
        String message = ex.getMessage();
        log.info(message);
        return new RestException(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public RestException handeValidationException(ValidationException ex) {
        String message = ex.getMessage();
        log.info(message);
        return new RestException(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestException handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Неизвестная ошибка при обработке параметра.");

        log.info(message);
        return new RestException(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestException handleAllExceptions(Exception ex) {
        String message = ex.getMessage();
        log.info(message);
        return new RestException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}