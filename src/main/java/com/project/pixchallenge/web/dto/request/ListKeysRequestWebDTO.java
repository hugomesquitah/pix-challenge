package com.project.pixchallenge.web.dto.request;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListKeysRequestWebDTO {

    @Schema(description = "Id unico da chave PIX")
    private UUID id;

    @Schema(description = "Tipo de Chave")
    private KeyType keyType;

    @Schema(description = "Numero da Conta")
    @Digits(integer = 8, fraction = 0)
    private Integer accountNumber;

    @Schema(description = "Numero da agencia")
    @Digits(integer = 4, fraction = 0)
    private Integer branchNumber;

    @Schema(description = "Nome do titular da conta")
    @Size(min = 1, max = 30)
    private String name;

    @Schema(description = "Data e hora de registro da chave")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Schema(description = "Data e hora de inativação da chave")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime inactivationDate;

    public Key toDomain() {
        return Key.builder()
                .id(id)
                .type(keyType)
                .accountNumber(accountNumber)
                .branchNumber(branchNumber)
                .name(name)
                .createdAt(createdAt)
                .inactivationDate(inactivationDate)
                .build();
    }

}
