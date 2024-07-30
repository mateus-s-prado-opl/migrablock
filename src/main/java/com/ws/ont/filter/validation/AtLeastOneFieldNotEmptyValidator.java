package com.ws.ont.filter.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtLeastOneFieldNotEmptyValidator implements ConstraintValidator<AtLeastOneFieldNotEmpty, BaseOntFilter> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneFieldNotEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(BaseOntFilter baseOntFilter, ConstraintValidatorContext context) {
        boolean isValid = false;
        for (String fieldName : fields) {
            try {
                String fieldValue = (String) new BeanWrapperImpl(baseOntFilter).getPropertyValue(fieldName);
                if (fieldValue != null && !fieldValue.trim().isEmpty()) {
                    isValid = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fields[0])
                    .addConstraintViolation();
        }

        return isValid;
    }
}
