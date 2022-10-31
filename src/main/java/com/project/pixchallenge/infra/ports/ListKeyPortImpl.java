package com.project.pixchallenge.infra.ports;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyPage;
import com.project.pixchallenge.core.ports.ListKeyPort;
import com.project.pixchallenge.infra.entities.KeyEntity;
import com.project.pixchallenge.infra.repositories.KeyRepository;
import com.project.pixchallenge.infra.repositories.specifications.KeyEntitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListKeyPortImpl implements ListKeyPort {

    private final KeyRepository repository;

    @Override
    public KeyPage list(final Key key,
                        final int page,
                        final int size) {

        var keyEntityPage = repository.findAll(
                new KeyEntitySpecification(key),
                PageRequest.of(page, size));

        return toKeyPage(keyEntityPage);
    }

    private KeyPage toKeyPage(Page<KeyEntity> keyEntityPage) {
        return KeyPage.builder()
                .hasNext(keyEntityPage.hasNext())
                .totalCount(keyEntityPage.getTotalElements())
                .offset(keyEntityPage.getPageable().getOffset())
                .pageSize(keyEntityPage.getPageable().getPageSize())
                .keys(keyEntityPage.map(KeyEntity::toKey).toList())
                .build();
    }
}
