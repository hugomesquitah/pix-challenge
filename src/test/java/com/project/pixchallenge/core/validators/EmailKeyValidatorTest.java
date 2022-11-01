package com.project.pixchallenge.core.validators;

import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.validators.keyType.EmailKeyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailKeyValidatorTest {

    private EmailKeyValidator validator;

    @BeforeEach
    public void setup() {
        validator = new EmailKeyValidator();
    }

    @Test
    public void testValidator() {
        Assertions.assertDoesNotThrow(() -> validator.validate("teste@teste.com"));
    }

    @Test
    public void testValidatorEmpty() {
        assertThrows(KeyValidatorException.class, () -> validator.validate(""));
    }

    @Test
    public void testValidatorBiggerThen77() {
        assertThrows(KeyValidatorException.class, () -> validator.validate(
                "snigcmwnituyvb384bty83bv7y4tn4387v485ht84n5f83n235erglnsviut843ht8dtgs76r6@test.com"));
    }

    @Test
    public void testValidatorInvalid() {
        assertThrows(KeyValidatorException.class, () -> validator.validate("test@"));
    }

    @Test
    public void testValidatorInvalid2() {
        assertThrows(KeyValidatorException.class, () -> validator.validate("testteste.com"));
    }
}
