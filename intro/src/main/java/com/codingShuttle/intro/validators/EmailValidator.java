package com.codingShuttle.intro.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email cannot be empty").addConstraintViolation();
            return false;
        }

        if (!email.contains("@")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email must contain '@' symbol").addConstraintViolation();
            return false;
        }

        if (!email.contains(".")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email must contain a domain (e.g., '.com')").addConstraintViolation();
            return false;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid email format. Example: user@example.com")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
