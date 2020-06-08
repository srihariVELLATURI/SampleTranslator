package com.sampleapp.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sampleapp.model.payload.PasswordResetRequest;
import com.sampleapp.validation.annotation.MatchPassword;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, PasswordResetRequest> {

    private Boolean allowNull;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(PasswordResetRequest value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String confirmPassword = value.getConfirmPassword();
        if (allowNull) {
            return null == password && null == confirmPassword;
        }
        return password.equals(confirmPassword);
    }
}
