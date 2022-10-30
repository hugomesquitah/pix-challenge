package com.project.pixchallenge.core.validators;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import org.apache.commons.lang3.StringUtils;

public class FormatValidator {

    public static void isNumericValidate(final String value, final String type) {
        if (!StringUtils.isNumeric(value)) {
            throw new KeyValidatorException(String.format("The %s must contains only numbers", type));
        }
    }

    public static void maxLengthValidate(final String value, final String type, final int maxLength) {
        if (value.length() > maxLength) {
            throw new KeyValidatorException(String
                    .format("The number of characters in the %s must be less than %d", type, maxLength));
        }
    }

    public static void emptyBlankValidator(final String value, final String type) {
        if (value.isEmpty() || value.isBlank()) {
            throw new KeyValidatorException(String.format("The %s cannot be empty or blank", type));
        }
    }
}
