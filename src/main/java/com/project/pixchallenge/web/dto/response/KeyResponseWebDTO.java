package com.project.pixchallenge.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.pixchallenge.core.domain.AccountType;
import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyResponseWebDTO {

    @Schema(description = "Id unico da chave PIX", required = true)
    private UUID id;

    @Schema(description = "Tipo de Chave", required = true)
    private KeyType keyType;

    @Schema(description = "Chave de endereçamento para conta transacional", required = true)
    private String keyValue;

    @Schema(description = "Numero da Conta", required = true)
    private Integer accountNumber;

    @Schema(description = "Tipo da Conta", required = true)
    private AccountType accountType;

    @Schema(description = "Numero da agencia", required = true)
    private Integer branchNumber;

    @Schema(description = "Nome do titular da conta", required = true)
    private String name;

    @Schema(description = "Sobrenome do titular da conta")
    private String lastName;

    @Schema(description = "Data e hora de registro da chave", required = true)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @Schema(description = "Data e hora de inativação da chave", required = true)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime inactivationDate;

    public static KeyResponseWebDTO from(final Key key) {
        return KeyResponseWebDTO.builder()
                .id(key.getId())
                .keyType(key.getType())
                .keyValue(key.getValue())
                .accountNumber(key.getAccountNumber())
                .accountType(key.getAccountType())
                .branchNumber(key.getBranchNumber())
                .name(key.getName())
                .lastName(key.getLastName() == null ? "" : key.getLastName())
                .createdAt(key.getCreatedAt())
                .inactivationDate(key.getInactivationDate())
                .build();
    }
}
