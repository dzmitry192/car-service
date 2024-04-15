package com.innowise.sivachenko.validation;

import com.innowise.sivachenko.validation.api.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        return year != null && year >= 1885 && year <= LocalDate.now().getYear();
    }
}
