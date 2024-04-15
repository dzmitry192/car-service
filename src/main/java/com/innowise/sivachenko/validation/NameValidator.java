package com.innowise.sivachenko.validation;

import com.innowise.sivachenko.validation.api.ValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name != null) {
            return name.matches("^$|[a-zA-Zа-яА-Я0-9]*$");
        }
        return false;
    }
}
