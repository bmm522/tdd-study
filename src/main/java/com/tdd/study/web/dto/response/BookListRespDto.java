package com.tdd.study.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListRespDto {

    List<BookResDto> items;

    @Builder
    public BookListRespDto(List<BookResDto> bookResDtoList) {
        this.items = bookResDtoList;
    }
}
