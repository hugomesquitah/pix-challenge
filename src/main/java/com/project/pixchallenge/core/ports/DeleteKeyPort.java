package com.project.pixchallenge.core.ports;

import com.project.pixchallenge.core.domain.Key;

import java.util.UUID;

public interface DeleteKeyPort {

    Key delete(UUID id);
}
