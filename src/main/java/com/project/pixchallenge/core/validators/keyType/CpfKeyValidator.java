package com.project.pixchallenge.core.validators.keyType;

import br.com.caelum.stella.validation.CPFValidator;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.FormatValidator;

public class CpfKeyValidator implements KeyValidator {

    private static final String TYPE_NAME = "CPF";
    private static final int MAX_CPF_SIZE = 11;

    @Override
    public void validate(final String value) {
        FormatValidator.emptyBlankValidator(value, TYPE_NAME);
        FormatValidator.isNumericValidate(value, TYPE_NAME);
        FormatValidator.maxLengthValidate(value, TYPE_NAME, MAX_CPF_SIZE);

        try {
            final var cpfValidator = new CPFValidator();
            cpfValidator.assertValid(value);
        } catch (Exception e) {
            throw new KeyValidatorException("Invalid CPF: " + value, e);
        }
    }
}
