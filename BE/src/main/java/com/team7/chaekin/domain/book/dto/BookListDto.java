package com.team7.chaekin.domain.book.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookListDto {
    private long bookId;
    private String title;
    private String author;
    private String cover;
}
