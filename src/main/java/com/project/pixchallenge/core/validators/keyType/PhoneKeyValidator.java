package com.project.pixchallenge.core.validators.keyType;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.FormatValidator;

public class PhoneKeyValidator implements KeyValidator {

    private static final String TYPE_NAME = "Phone";
    private static final String FORMAT_PHONE_REGEX = "^\\+[0-9]{1,2}[0-9]{1,3}[0-9]{9}$";

    @Override
    public void validate(final String value) {
        FormatValidator.emptyBlankValidator(value, TYPE_NAME);

        if (!value.matches(FORMAT_PHONE_REGEX)) {
            throw new KeyValidatorException("Invalid Phone" + value);
        }

    }
}
