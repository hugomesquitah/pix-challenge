package com.project.pixchallenge.core.validators.keyType;

import br.com.caelum.stella.validation.CNPJValidator;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.FormatValidator;

public class CnpjKeyValidator implements KeyValidator {

    private static final String TYPE_NAME = "CNPJ";
    private static final int MAX_CNPJ_SIZE = 14;

    @Override
    public void validate(final String value) {
        FormatValidator.emptyBlankValidator(value, TYPE_NAME);
        FormatValidator.isNumericValidate(value, TYPE_NAME);
        FormatValidator.maxLengthValidate(value, TYPE_NAME, MAX_CNPJ_SIZE);

        try {
            final var validator = new CNPJValidator();
            validator.assertValid(value);
        } catch (Exception e) {
            throw new KeyValidatorException("Invalid CNPJ: " + value, e);
        }
    }
}
