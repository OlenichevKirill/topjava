package ru.javawebinar.topjava.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class JdbcValidatorUtil {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private static final Validator validator = validatorFactory.getValidator();

    private JdbcValidatorUtil() {
    }

    public static <T> void validate(T entity) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
