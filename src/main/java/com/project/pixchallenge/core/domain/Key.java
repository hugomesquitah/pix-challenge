package com.project.pixchallenge.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Key {

    private UUID id;
    private KeyType type;
    private String value;
    private AccountType accountType;
    private Integer accountNumber;
    private Integer branchNumber;
    private String name;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime inactivationDate;
    private KeyStatus status;

}
