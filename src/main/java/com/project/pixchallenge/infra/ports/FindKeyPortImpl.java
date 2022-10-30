package com.project.pixchallenge.infra.ports;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.ports.FindKeyPort;
import com.project.pixchallenge.infra.entities.KeyEntity;
import com.project.pixchallenge.infra.repositories.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindKeyPortImpl implements FindKeyPort {

    private final KeyRepository repository;

    @Override
    public List<Key> findByAccount(Integer accountNumber, Integer branchNumber) {
        return repository.findByAccountNumberAndBranchNumber(accountNumber, branchNumber)
                .stream()
                .map(KeyEntity::toKey)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Key> findByValue(String keyValue) {
        return repository.findByValue(keyValue)
                .map(KeyEntity::toKey);
    }

}
