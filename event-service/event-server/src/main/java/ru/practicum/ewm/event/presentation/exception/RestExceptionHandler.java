package ru.practicum.ewm.event.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.business.exception.ValidationException;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConversionFailedException.class)
    public RestException handeConversionFailedException(ConversionFailedException ex) {
        String message = ex.getMessage();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .reason("Недопустимый запрос")
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public RestException handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .reason("Нарушение ограничения целостности")
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public RestException handleValidationException(ValidationException ex) {
        String message = ex.getMessage();
        String reason = ex.getReason();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .reason(reason)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public RestException handleConstraintViolationException(NotFoundException ex) {
        String message = ex.getMessage();
        log.info(message);

        return RestException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .reason("Запрашиваемый объект не найден")
                .build();
    }


//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public RestException handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//
//        String message = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .findFirst()
//                .map(FieldError::getDefaultMessage)
//                .orElse("Неизвестная ошибка при обработке параметра.");
//
//        log.info(message);
//        return new RestException(message, HttpStatus.BAD_REQUEST);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RestException handleAllExceptions(Exception ex) {
//        String message = ex.getMessage();
//        log.info(message);
//        return new RestException(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}