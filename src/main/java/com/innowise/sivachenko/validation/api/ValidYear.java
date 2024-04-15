package com.innowise.sivachenko.validation.api;

import com.innowise.sivachenko.validation.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface ValidYear {
    String message() default "Invalid value in field year";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
