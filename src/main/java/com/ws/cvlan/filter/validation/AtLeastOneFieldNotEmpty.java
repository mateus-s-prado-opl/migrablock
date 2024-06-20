package com.ws.cvlan.filter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = AtLeastOneFieldNotEmptyValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AtLeastOneFieldNotEmptyContainer.class)
public @interface AtLeastOneFieldNotEmpty {

    String message() default "At least one of the specified fields must be filled";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Fields to validate
    String[] fields();

}

