package ru.javawebinar.topjava.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidatorUtil() {
    }

    public static <T> void validate(T entity) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
