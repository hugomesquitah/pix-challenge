package com.project.pixchallenge.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KeyPage {

    private Boolean hasNext;
    private long totalCount;
    private long offset;
    private int pageSize;
    private List<Key> keys;

}
