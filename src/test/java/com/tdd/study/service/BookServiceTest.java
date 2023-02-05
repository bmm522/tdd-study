package com.tdd.study.service;

import com.tdd.study.domain.Book;
import com.tdd.study.domain.BookRepository;
import com.tdd.study.util.MailSender;
import com.tdd.study.web.dto.response.BookResDto;
import com.tdd.study.web.dto.request.BookSaveReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("책 목록보기 테스트")
    public void selectBooksTest(){
        // given

        // stub (가설)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "김지인"));
        books.add(new Book(2L, "spring강의", "김지인"));

        when(bookRepository.findAll()).thenReturn(books);

        // when (실행)
        List<BookResDto> dtos = bookService.selectBooks();


        // then
        assertThat(dtos.get(0).getTitle()).isEqualTo("junit강의");
        assertThat(dtos.get(0).getAuthor()).isEqualTo("김지인");
        assertThat(dtos.get(1).getTitle()).isEqualTo("spring강의");
        assertThat(dtos.get(1).getAuthor()).isEqualTo("김지인");
    }

    // 3. 책 한건보기 테스트
    @Test
    @DisplayName("책 한건보기 테스트")
    public void selectBookTest(){
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "junit강의", "김지인");
        Optional<Book> bookOp = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOp);
        // when
        BookResDto bookResDto = bookService.selectBook(id);

        // then
        assertThat(bookResDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookResDto.getAuthor()).isEqualTo(book.getAuthor());

    }

    // 3. 책 수정하기
    @DisplayName("책 수정하기 테스트")
    @Test
    public void updateBookTest(){
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("김지인");
        // stub
        Book book = new Book(1L, "spring강의", "김지인");
        Optional<Book> bookOp = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOp);

        // when
        BookResDto bookResDto = bookService.updateBook(id, dto);

        // then

        assertThat(bookResDto.getTitle()).isEqualTo(dto.getTitle());
    }
}
