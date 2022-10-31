package com.project.pixchallenge.infra.ports;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.ports.UpdateKeyPort;
import com.project.pixchallenge.infra.entities.KeyEntity;
import com.project.pixchallenge.infra.repositories.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class UpdateKeyPortImpl implements UpdateKeyPort {

    private final KeyRepository repository;

    @Override
    public Key update(final Key key) {
        var activeKey = repository.findByIdAndActiveIsTrue(key.getId())
                .orElseThrow(() -> new EntityNotFoundException("Key id not found"));

        return repository.save(updateFields(key, activeKey)).toKey();
    }

    private KeyEntity updateFields(Key key, KeyEntity keyEntity) {
        keyEntity.setAccountType(key.getAccountType());
        keyEntity.setAccountNumber(key.getAccountNumber());
        keyEntity.setBranchNumber(key.getBranchNumber());
        keyEntity.setName(key.getName());
        keyEntity.setLastName(key.getLastName() == null ? keyEntity.getLastName() : key.getLastName());

        return keyEntity;
    }
}
