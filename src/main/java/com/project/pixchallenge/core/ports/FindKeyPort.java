package com.project.pixchallenge.core.ports;

import com.project.pixchallenge.core.domain.Key;

import java.util.List;
import java.util.Optional;

public interface FindKeyPort {

    List<Key> findByAccount(Integer accountNumber, Integer branchNumber);

    Optional<Key> findByValue(String keyValue);
}
