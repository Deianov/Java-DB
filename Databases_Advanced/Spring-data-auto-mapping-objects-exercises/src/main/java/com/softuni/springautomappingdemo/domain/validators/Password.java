package com.softuni.springautomappingdemo.domain.validators;

import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.softuni.springautomappingdemo.constants.ValidationConstants.*;

@Component
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default PASSWORD_TOO_SIMPLE;

    int minLength() default USER_PASSWORD_MIN_LENGTH;

    int maxLength() default USER_PASSWORD_MAX_LENGTH;

    boolean containsDigit() default USER_PASSWORD_SHOULD_CONTAIN_DIGIT;

    boolean containsLowerCase() default USER_PASSWORD_SHOULD_CONTAIN_LOWER_CASE;

    boolean containsUpperCase() default USER_PASSWORD_SHOULD_CONTAIN_UPPER_CASE;

    boolean nullable() default USER_PASSWORD_CAN_BE_OMITTED;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
