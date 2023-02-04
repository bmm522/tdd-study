package com.tdd.study.domain;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {



    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 등록 테스트")
    public void insertBookTest(){
        // given (데이터 준비)
        String title = "junit5";
        String author = "김지인";

        Book book = Book.builder()
                .title(title)
                        .author(author)
                                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)

        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }
}
