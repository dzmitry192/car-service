package com.innowise.sivachenko.validation;

import com.innowise.sivachenko.validation.api.ValidEngineCapacity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EngineCapacityValidator implements ConstraintValidator<ValidEngineCapacity, Float> {
    @Override
    public boolean isValid(Float engineCapacity, ConstraintValidatorContext context) {
        return engineCapacity != null && engineCapacity > 0;
    }
}
