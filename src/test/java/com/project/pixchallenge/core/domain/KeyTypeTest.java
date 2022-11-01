package com.project.pixchallenge.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyTypeTest {

    private KeyType type;

    @Test
    void when_getMaxKeysOfCpf_expect_five() {
        type = KeyType.CPF;
        assertEquals(5, type.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfPhone_expect_five() {
        type = KeyType.PHONE;
        assertEquals(5, type.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfEmail_expect_five() {
        type = KeyType.EMAIL;
        assertEquals(5, type.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfRandom_expect_five() {
        type = KeyType.RANDOM;
        assertEquals(5, type.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfCnpj_expect_twenty() {
        type = KeyType.CNPJ;
        assertEquals(20, type.getMaxKeys());
    }
}
