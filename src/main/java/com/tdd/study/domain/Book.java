package com.tdd.study.domain;


import com.tdd.study.web.dto.response.BookResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 20, nullable = false)
    private String author;

    @Builder
    public Book(long id, String title, String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void update(String title, String author){
        this.title = title;
        this.author = author;
    }

    public BookResDto toDto(){
        return BookResDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();
    }
}
