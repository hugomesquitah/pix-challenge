package com.project.pixchallenge.core.validators;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.keyType.RandomKeyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RandomKeyValidatorTest {

    private RandomKeyValidator validator;

    @BeforeEach
    public void setup() {
        validator = new RandomKeyValidator();
    }

    @Test
    public void testValidator() {
        Assertions.assertDoesNotThrow(() -> validator.validate(UUID.randomUUID().toString()));
    }

    @Test
    public void testValidatorEmpty() {
        assertThrows(KeyValidatorException.class, () -> validator.validate(""));
    }

    @Test
    public void testValidatorInvalid() {
        assertThrows(KeyValidatorException.class, () -> validator.validate("91c44c01-0566-4b31-baa2"));
    }
}
