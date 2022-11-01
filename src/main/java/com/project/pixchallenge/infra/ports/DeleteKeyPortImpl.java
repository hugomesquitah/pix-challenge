package com.project.pixchallenge.infra.ports;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.ports.DeleteKeyPort;
import com.project.pixchallenge.infra.repositories.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteKeyPortImpl implements DeleteKeyPort {

    private final KeyRepository repository;

    @Override
    public Key delete(final UUID id) {
        var foundKey = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Key id not found"));

        if (!foundKey.isActive()) {
            throw new KeyValidatorException(String.format("The key with id %s is already inactive", id));
        }

        return repository.save(foundKey.delete()).toKey();
    }
}
