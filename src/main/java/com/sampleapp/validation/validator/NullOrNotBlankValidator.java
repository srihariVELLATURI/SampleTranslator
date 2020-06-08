package com.sampleapp.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sampleapp.validation.annotation.NullOrNotBlank;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

    @Override
    public void initialize(NullOrNotBlank parameters) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }

        boolean isAllWhitespace = value.matches("^\\s*$");
        return !isAllWhitespace;
    }
}
