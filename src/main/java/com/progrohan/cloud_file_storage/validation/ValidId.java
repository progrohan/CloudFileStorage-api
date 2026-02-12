package com.progrohan.cloud_file_storage.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Positive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Positive(message = "Id must be positive")
public @interface ValidId {
    String message() default "Invalid id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
