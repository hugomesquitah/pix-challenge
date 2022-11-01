package com.project.pixchallenge.core.validators.keyType;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.FormatValidator;

public class RandomKeyValidator implements KeyValidator {

    private static final String TYPE_NAME = "Random Key";
    private static final int MAX_RANDOM_KEY_SIZE = 36;

    @Override
    public void validate(final String value) {
        FormatValidator.emptyBlankValidator(value, TYPE_NAME);
        FormatValidator.maxLengthValidate(value, TYPE_NAME, MAX_RANDOM_KEY_SIZE);

        if (!value.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
            throw new KeyValidatorException("Invalid Random Key: " + value);
        }
    }
}
