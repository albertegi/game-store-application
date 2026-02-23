package com.alvirg.store.common;

import lombok.*;
import org.springframework.core.SpringVersion;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T>{

    private List<T> content;
    private int totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
}
