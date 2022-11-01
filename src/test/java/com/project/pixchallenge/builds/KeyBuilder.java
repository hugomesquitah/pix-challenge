package com.project.pixchallenge.builds;

import com.project.pixchallenge.core.domain.AccountType;
import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyType;

import java.time.LocalDateTime;
import java.util.UUID;

public final class KeyBuilder {

    public static Key cpfCreating() {
        return Key.builder()
                .value("01649757050")
                .type(KeyType.CPF)
                .accountType(AccountType.CHECKING)
                .accountNumber(12345678)
                .branchNumber(1234)
                .name("Fulano")
                .lastName("de Tal")
                .build();
    }

    public static Key cnpjCreating() {
        return Key.builder()
                .value("99850394000134")
                .type(KeyType.CNPJ)
                .accountType(AccountType.CHECKING)
                .accountNumber(12345678)
                .branchNumber(1234)
                .name("Empresa LTDA")
                .build();
    }

    public static Key cpfCreated(){
        return Key.builder()
                .value("01649757050")
                .type(KeyType.CPF)
                .accountType(AccountType.CHECKING)
                .accountNumber(12345678)
                .branchNumber(1234)
                .name("Fulano")
                .lastName("de Tal")
                .id(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();
    }
}
