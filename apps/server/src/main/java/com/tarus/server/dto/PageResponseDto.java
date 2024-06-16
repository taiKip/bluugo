package com.tarus.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageResponseDto <T>{
    private List<T> content;
    private int pageNo;
    private int pageSize;
    private Long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrev;
}
