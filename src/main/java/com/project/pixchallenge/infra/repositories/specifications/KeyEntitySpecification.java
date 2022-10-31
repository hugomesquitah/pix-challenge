package com.project.pixchallenge.infra.repositories.specifications;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.infra.entities.KeyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Objects;

@RequiredArgsConstructor
public class KeyEntitySpecification implements Specification<KeyEntity> {

    private final Key key;

    @Override
    public Predicate toPredicate(Root<KeyEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = new ArrayList<Predicate>();

        if (Objects.nonNull(key.getId()))
            predicates.add(criteriaBuilder.equal(root.get("id"), key.getId()));
        if (Objects.nonNull(key.getType()))
            predicates.add(criteriaBuilder.equal(root.get("type"), key.getType()));
        if (Objects.nonNull(key.getAccountNumber()))
            predicates.add(criteriaBuilder.equal(root.get("accountNumber"), key.getAccountNumber()));
        if (Objects.nonNull(key.getBranchNumber()))
            predicates.add(criteriaBuilder.equal(root.get("branchNumber"), key.getBranchNumber()));
        if (Objects.nonNull(key.getName()))
            predicates.add(criteriaBuilder.equal(root.get("name"), key.getName()));
        if (Objects.nonNull(key.getCreatedAt()))
            predicates.add(criteriaBuilder.between(root.get("createdAt"), key.getCreatedAt(), key.getCreatedAt()));
        if (Objects.nonNull(key.getInactivationDate()))
            predicates.add(criteriaBuilder.between(root.get("inactivationDate"), key.getInactivationDate(), key.getInactivationDate()));

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
