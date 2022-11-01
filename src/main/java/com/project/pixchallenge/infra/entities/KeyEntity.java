package com.project.pixchallenge.infra.entities;

import com.project.pixchallenge.core.domain.AccountType;
import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pix_keys")
public class KeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(name = "key_type", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private KeyType type;

    @Column(name = "key_value", updatable = false, nullable = false)
    private String value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private Integer accountNumber;

    @Column(nullable = false)
    private Integer branchNumber;

    @Column(nullable = false)
    private String name;

    @Column
    private String lastName;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime inactivationDate;

    @Column(nullable = false)
    private boolean active;

    public static KeyEntity from(final Key key) {
        return KeyEntity.builder()
                .type(key.getType())
                .value(key.getValue())
                .accountType(key.getAccountType())
                .accountNumber(key.getAccountNumber())
                .branchNumber(key.getBranchNumber())
                .name(key.getName())
                .lastName(key.getLastName())
                .active(key.isActive())
                .build();
    }

    public Key toKey() {
        return Key.builder()
                .id(id)
                .type(type)
                .value(value)
                .accountType(accountType)
                .accountNumber(accountNumber)
                .branchNumber(branchNumber)
                .name(name)
                .lastName(lastName)
                .createdAt(createdAt)
                .updateAt(updatedAt)
                .inactivationDate(inactivationDate)
                .active(active)
                .build();
    }

    public KeyEntity update(final Key key) {
        this.accountType = key.getAccountType();
        this.accountNumber = key.getAccountNumber();
        this.branchNumber = key.getBranchNumber();
        this.name = key.getName();
        this.lastName = key.getLastName() == null ? this.getLastName() : key.getLastName();

        return this;
    }

    public KeyEntity delete() {
        this.active = false;
        this.inactivationDate = LocalDateTime.now();

        return this;
    }

}
