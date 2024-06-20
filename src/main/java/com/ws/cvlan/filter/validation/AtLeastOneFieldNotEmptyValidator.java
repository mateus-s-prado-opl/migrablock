package com.ws.cvlan.filter.validation;

import com.ws.cvlan.filter.AddCvlanBlockFilter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class AtLeastOneFieldNotEmptyValidator implements ConstraintValidator<AtLeastOneFieldNotEmpty, AddCvlanBlockFilter> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneFieldNotEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(AddCvlanBlockFilter addCvlanBlockFilter, ConstraintValidatorContext context) {
        boolean isValid = false;
        for (String fieldName : fields) {
            try {
                String fieldValue = (String) new BeanWrapperImpl(addCvlanBlockFilter).getPropertyValue(fieldName);
                if (fieldValue != null && !fieldValue.trim().isEmpty()) {
                    isValid = true;
                    break;
                }
            } catch (Exception e) {
                // Tratamento de exceção, se necessário
                isValid = false;
            }
        }

        if (!isValid) {
            // Define o contexto de validação para a mensagem personalizada
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fields[0]) // Escolha um campo para associar a mensagem de erro
                    .addConstraintViolation();
        }

        return isValid;
    }
}
