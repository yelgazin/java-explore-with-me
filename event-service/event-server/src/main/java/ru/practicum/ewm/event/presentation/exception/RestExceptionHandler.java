package ru.practicum.ewm.event.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.business.exception.ValidationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConversionFailedException.class)
    public RestException handeConversionFailedException(ConversionFailedException ex) {
        String message = ex.getLocalizedMessage();
        String reason = "Недопустимый запрос.";
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public RestException handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getLocalizedMessage();
        String reason = "Нарушение ограничения целостности.";
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = BusinessLogicException.class)
    public RestException handleBusinessLogicException(BusinessLogicException ex) {
        String message = ex.getLocalizedMessage();
        String reason = ex.getReason();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public RestException handleValidationException(ValidationException ex) {
        String message = ex.getLocalizedMessage();
        String reason = ex.getReason();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public RestException handleConstraintViolationException(NotFoundException ex) {
        String message = ex.getLocalizedMessage();
        String reason = ex.getReason();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestException handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getLocalizedMessage();
        String reason = "Ошибка валидации";
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestException handleAllExceptions(Exception ex) {
        String message = ex.getMessage();
        String reason = "Неизвестная причина.";
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .reason(reason)
                .build();
    }
}