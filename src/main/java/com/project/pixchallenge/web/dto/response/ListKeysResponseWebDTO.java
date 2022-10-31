package com.project.pixchallenge.web.dto.response;

import com.project.pixchallenge.core.domain.KeyPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ListKeysResponseWebDTO {

    private boolean hasNext;
    private long totalCount;
    private long offset;
    private int pageSize;
    private List<KeyResponseWebDTO> keys;

    public static ListKeysResponseWebDTO from(final KeyPage keyPage) {
        return ListKeysResponseWebDTO.builder()
                .hasNext(keyPage.getHasNext())
                .totalCount(keyPage.getTotalCount())
                .offset(keyPage.getOffset())
                .pageSize(keyPage.getPageSize())
                .keys(keyPage.getKeys()
                        .stream()
                        .map(KeyResponseWebDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }

}
