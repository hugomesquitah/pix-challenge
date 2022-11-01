package com.project.pixchallenge.builds;

import com.project.pixchallenge.core.domain.AccountType;
import com.project.pixchallenge.core.domain.KeyType;
import com.project.pixchallenge.web.dto.request.CreateKeyRequestWebDTO;

public final class KeyWebDTOBuilder {

    public static CreateKeyRequestWebDTO cpfCreate() {
        return CreateKeyRequestWebDTO.builder()
                .keyValue("01649757050")
                .keyType(KeyType.CPF)
                .accountType(AccountType.CHECKING)
                .accountNumber(12345678)
                .branchNumber(1234)
                .name("Fulano")
                .lastName("de Tal")
                .build();
    }
}
