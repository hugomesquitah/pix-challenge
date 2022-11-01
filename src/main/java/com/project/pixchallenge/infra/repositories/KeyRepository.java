package com.project.pixchallenge.infra.repositories;

import com.project.pixchallenge.infra.entities.KeyEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeyRepository extends PagingAndSortingRepository<KeyEntity, UUID>, JpaSpecificationExecutor<KeyEntity> {

    List<KeyEntity> findByAccountNumberAndBranchNumber(Integer accountNumber, Integer branchNumber);

    Optional<KeyEntity> findByValue(String value);

    Optional<KeyEntity> findByIdAndActiveIsTrue(UUID id);
}
