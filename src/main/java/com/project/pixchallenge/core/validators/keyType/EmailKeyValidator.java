package com.project.pixchallenge.core.validators.keyType;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.FormatValidator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailKeyValidator implements KeyValidator {

    public static final String TYPE_NAME = "E-mail";
    public static final int MAX_EMAIL_SIZE = 77;

    @Override
    public void validate(String value) {
        FormatValidator.emptyBlankValidator(value, TYPE_NAME);
        FormatValidator.maxLengthValidate(value, TYPE_NAME, MAX_EMAIL_SIZE);

        try {
            var address = new InternetAddress(value);
            address.validate();
        } catch (AddressException e) {
            throw new KeyValidatorException("Invalid E-mail: " + value, e);
        }

    }
}
