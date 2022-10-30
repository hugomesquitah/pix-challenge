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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateKeyRequestWebDTO {

    @Schema(description = "Chave de endereçamento para conta transacional", required = true)
    @NotNull
    private String keyValue;

    @Schema(description = "Tipo de Chave", allowableValues = {"PHONE", "EMAIL", "CPF", "CNPJ", "RANDOM"}, required = true)
    @NotNull
    private KeyType keyType;

    @Schema(description = "Tipo da Conta", allowableValues = {"CHECKING", "SAVINGS"}, required = true)
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
    private String name;

    @Schema(description = "Sobrenome do titular da conta")
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
