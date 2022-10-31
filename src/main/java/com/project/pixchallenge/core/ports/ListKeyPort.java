package com.project.pixchallenge.core.ports;

import com.project.pixchallenge.core.domain.Key;
import com.project.pixchallenge.core.domain.KeyPage;

public interface ListKeyPort {

    KeyPage list(Key key, int page, int size);
}
