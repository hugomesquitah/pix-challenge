package com.project.pixchallenge.core.usecases;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.exception.KeyValidatorException;
import com.project.pixchallenge.core.ports.FindKeyPort;
import com.project.pixchallenge.core.ports.SaveKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateKeyUseCase {

    private final FindKeyPort findKeyPort;
    private final SaveKeyPort saveKeyPort;

    public Key execute(final Key key) {
        log.info("CreateKey_validating");

        validateKeyExists(key);
        validateMaxKeys(key);

        log.info("CreateKey_saving");

        key.markAsActive();
        return saveKeyPort.save(key);
    }

    private void validateKeyExists(final Key key) {
        findKeyPort.findByValue(key.getValue())
                .ifPresent(keyExisting -> {
                    throw new KeyValidatorException(
                            String.format("The %s key already exists in the database", keyExisting.getValue()));
                });
    }

    private void validateMaxKeys(final Key key) {
        var keyExisting = findKeyPort.findByAccount(key.getAccountNumber(), key.getBranchNumber());

        if (keyExisting.size() > key.getType().getMaxKeys()) {
            throw new KeyValidatorException("The number of keys for this account has been exceeded");
        }
    }
}
