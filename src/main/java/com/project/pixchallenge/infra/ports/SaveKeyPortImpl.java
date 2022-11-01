package com.project.pixchallenge.infra.ports;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.ports.SaveKeyPort;
import com.project.pixchallenge.infra.entities.KeyEntity;
import com.project.pixchallenge.infra.repositories.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveKeyPortImpl implements SaveKeyPort {

    private final KeyRepository repository;

    @Override
    public Key save(final Key key) {
        return repository.save(KeyEntity.from(key)).toKey();
    }
}
