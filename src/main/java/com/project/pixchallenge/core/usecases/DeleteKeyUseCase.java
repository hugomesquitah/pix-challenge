package com.project.pixchallenge.core.usecases;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.ports.DeleteKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteKeyUseCase {

    private final DeleteKeyPort deleteKeyPort;

    @Transactional
    public Key execute(final UUID id) {
        return deleteKeyPort.delete(id);
    }
}
