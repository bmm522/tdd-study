package com.tdd.study.service;

import com.tdd.study.domain.BookRepository;
import com.tdd.study.util.MailSenderStub;
import com.tdd.study.web.dto.BookResDto;
import com.tdd.study.web.dto.BookSaveReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;
    @Test
    @DisplayName("책 등록하기 서비스 테스트")
    public void insertBookServiceTest(){
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("김지인");

        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookResDto bookResDto = bookService.insertBook(dto);

        // then
        assertEquals("스프링 강의", bookResDto.getTitle());
        assertEquals(dto.getAuthor(), bookResDto.getAuthor());
    }
}
