package com.project.pixchallenge.core.usecases;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyPage;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.ports.ListKeyPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ListKeyUseCase {

    private final ListKeyPort listKeyPort;

    public KeyPage execute(final Key key,
                           final int page,
                           final int size) {

        validateFilters(key);

        var keyPage = listKeyPort.list(key, page, size);

        if (keyPage.getKeys().isEmpty()) {
            throw new EntityNotFoundException("No Key Found");
        }

        return keyPage;

    }

    private void validateFilters(final Key key) {
        if (Objects.nonNull(key.getId()) && ObjectUtils.anyNotNull(
                key.getCreatedAt(), key.getInactivationDate(), key.getType(),
                key.getAccountNumber(), key.getBranchNumber(), key.getName())) {
            throw new KeyValidatorException("The filter by id must be unique");
        }

        if (ObjectUtils.allNotNull(key.getCreatedAt(), key.getInactivationDate())) {
            throw new KeyValidatorException("\'createdAt\' and \'inactivationDate\' filters should not be used together");
        }
    }
}
