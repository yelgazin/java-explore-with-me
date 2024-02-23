package ru.practicum.ewm.event.presentation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target({ElementType.FIELD})
public @interface NullOrNotBlank {
    String message() default "Значение должно быть пустым или содержать печатные символы.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
