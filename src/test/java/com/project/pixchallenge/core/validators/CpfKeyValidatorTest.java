package com.project.pixchallenge.core.validators;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.keyType.CpfKeyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CpfKeyValidatorTest {

    private CpfKeyValidator validator;

    @BeforeEach
    public void setup() {
        validator = new CpfKeyValidator();
    }

    @Test
    public void testValidator() {
        Assertions.assertDoesNotThrow(() -> validator.validate("02942765054"));
    }

    @Test
    public void testValidatorInvalid() {
        assertThrows(KeyValidatorException.class, () -> validator.validate("461.398.510-00"));
    }

    @Test
    public void testValidatorEmpty() {
        assertThrows(KeyValidatorException.class, () -> validator.validate(""));
    }

    @Test
    public void testValidatorWithPoints() {
        assertThrows(KeyValidatorException.class, () -> validator.validate("461.398.510-25"));
    }

    @Test
    public void testValidatorWithLettres() {
        assertThrows(KeyValidatorException.class, () -> validator.validate("4613ds9851025"));
    }
}
