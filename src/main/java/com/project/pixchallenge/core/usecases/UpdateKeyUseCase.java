package com.project.pixchallenge.core.usecases;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.ports.UpdateKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateKeyUseCase {

    private final UpdateKeyPort updateKeyPort;

    @Transactional
    public Key execute(final Key key) {
        return updateKeyPort.update(key);
    }

}
