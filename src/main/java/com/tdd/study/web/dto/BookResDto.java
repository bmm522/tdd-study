package com.tdd.study.web.dto;

import com.tdd.study.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookResDto {

    private Long id;
    private String title;
    private String author;

    public BookResDto toDto(Book bookPS){
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
}
