package com.tdd.study.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


// 1. 테스트 메서드들의 순서는 보장되지 않는다.
// 2. 테스트 메서드가 하나 실행 후 종료되면 데이터가 초기화 된다. (단 primary key auto_increment 값이 초기화가 안됨)

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
    @Sql("classpath:db/tableInit.sql")
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

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책 삭제")
    public void deleteBook(){
        Long id = 1L;

        bookRepository.deleteById(id);

        Optional<Book> bookPS = bookRepository.findById(id);

        assertFalse(bookPS.isPresent());
    }


    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("책 수정하기 테스트")
    public void updateBook(){
        Long id = 1L;
        String title = "junit5";
        String author = "겟인데어";
        Book book = new Book(id, title, author);

        Book bookPS = bookRepository.save(book);

//
//        bookRepository.findAll().stream()
//                .forEach(book2 -> {System.out.println(book2.getTitle());
//                    System.out.println(book2.getAuthor());
//                    System.out.println(book2.getId());
//                    System.out.println("===========================");});
//
//        System.out.println(bookPS.getTitle());
//        System.out.println(bookPS.getAuthor());
//        System.out.println(bookPS.getId());
//        System.out.println("===========================");

        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
}
