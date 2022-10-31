package com.project.pixchallenge.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyTypeTest {

    @Test
    void when_getMaxKeysOfCpf_expect_five() {
        assertEquals(5, KeyType.CPF.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfPhone_expect_five() {
        assertEquals(5, KeyType.PHONE.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfEmail_expect_five() {
        assertEquals(5, KeyType.EMAIL.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfRandom_expect_five() {
        assertEquals(5, KeyType.RANDOM.getMaxKeys());
    }

    @Test
    void when_getMaxKeysOfCnpj_expect_twenty() {
        assertEquals(20, KeyType.CNPJ.getMaxKeys());
    }
}
