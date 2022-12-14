package com.project.pixchallenge.web.dto.request;

import com.project.pixchallenge.core.domain.AccountType;
import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateKeyRequestWebDTO {

    @Schema(description = "Chave de endereçamento para conta transacional", required = true)
    @NotNull
    @Size(min = 1, max = 77)
    private String keyValue;

    @Schema(description = "Tipo de Chave", required = true)
    @NotNull
    private KeyType keyType;

    @Schema(description = "Tipo da Conta", required = true)
    @NotNull
    private AccountType accountType;

    @Schema(description = "Numero da Conta", required = true)
    @NotNull
    @Digits(integer = 8, fraction = 0)
    private Integer accountNumber;

    @Schema(description = "Numero da agencia", required = true)
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer branchNumber;

    @Schema(description = "Nome do titular da conta", required = true)
    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @Schema(description = "Sobrenome do titular da conta")
    @Size(min = 1, max = 45)
    private String lastName;

    public Key toDomain() {
        return Key.builder()
                .type(keyType)
                .value(keyValue)
                .accountType(accountType)
                .accountNumber(accountNumber)
                .branchNumber(branchNumber)
                .name(name)
                .lastName(lastName)
                .build();
    }

}
