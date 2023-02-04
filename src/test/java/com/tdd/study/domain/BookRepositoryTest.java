package com.tdd.study.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {


    // @BeforeAll // 테스트 시작전에 한번만 실행




    @Autowired // DI
    private BookRepository bookRepository;

    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void readyData(){
        String title = "junit5";
        String author = "김지인";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();


        Book bookPS = bookRepository.save(book);
    }


    // 1. 책 등록
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

    } // 트랙잭션 종료(저장된 데이터를 초기화함)

    // 2. 책 목록보기
    @Test
    @DisplayName("책 목록 보기 테스트")
    public void selectBookTest(){
        // given
        String title = "junit5";
        String author = "김지인";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertEquals("junit5", books.get(0).getTitle());
        assertEquals(author, books.get(0).getAuthor());
    } // 트랙잭션 종료 (저장된 데이터를 초기화함)

    // 3. 책 학건 보기
    @Test
    @DisplayName("책 한건 보기 테스트")
    public void SelectOneBookTest(){
        String title = "junit5";
        String author = "김지인";

        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }
}
