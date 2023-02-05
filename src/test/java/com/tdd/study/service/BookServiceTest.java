package com.tdd.study.service;

import com.tdd.study.domain.BookRepository;
import com.tdd.study.util.MailSender;
import com.tdd.study.util.MailSenderStub;
import com.tdd.study.web.dto.BookResDto;
import com.tdd.study.web.dto.BookSaveReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;



    @Test
    @DisplayName("책 등록하기 서비스 테스트")
    public void insertBookServiceTest(){
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("김지인");

        // stub : 행동 정의 (가설)
       when(bookRepository.save(any())).thenReturn(dto.toEntity());
       when(mailSender.send()).thenReturn(true);
        // when
        BookResDto bookResDto = bookService.insertBook(dto);

        // then
       // assertEquals("junit강의", bookResDto.getTitle());
       // assertEquals(dto.getAuthor(), bookResDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookResDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookResDto.getAuthor());
    }
}
