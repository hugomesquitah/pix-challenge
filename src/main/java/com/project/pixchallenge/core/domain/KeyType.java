package com.project.pixchallenge.core.domain;

import com.project.pixchallenge.core.validators.keyType.CnpjKeyValidator;
import com.project.pixchallenge.core.validators.keyType.CpfKeyValidator;
import com.project.pixchallenge.core.validators.keyType.EmailKeyValidator;
import com.project.pixchallenge.core.validators.keyType.KeyValidator;
import com.project.pixchallenge.core.validators.keyType.PhoneKeyValidator;
import com.project.pixchallenge.core.validators.keyType.RandomKeyValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyType {

    PHONE(new PhoneKeyValidator()),
    EMAIL(new EmailKeyValidator()),
    CPF(new CpfKeyValidator()),
    CNPJ(new CnpjKeyValidator()),
    RANDOM(new RandomKeyValidator());

    private final KeyValidator validator;

    public int getMaxKeys() {
        if (CNPJ.equals(this)) {
            return 20;
        }

        return 5;
    }
}
