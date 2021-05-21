package com.spring.store.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        return contactField != null && contactField.matches("^\\+?[78][-\\(]?\\d{3}\\)?-?\\d{3}-?\\d{2}-?\\d{2}$")
                && (contactField.length() > 10) && (contactField.length() < 17);
    }

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {

    }
}
