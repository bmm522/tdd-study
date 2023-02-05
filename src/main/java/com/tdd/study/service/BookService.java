package com.tdd.study.service;

import com.tdd.study.domain.Book;
import com.tdd.study.domain.BookRepository;
import com.tdd.study.util.MailSender;
import com.tdd.study.web.dto.BookResDto;
import com.tdd.study.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto insertBook(BookSaveReqDto dto){
        Book bookPs = bookRepository.save(dto.toEntity());
        if(bookPs != null){
            if(!mailSender.send()){
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return bookPs.toDto();
    }

    // 2. 책 목록보기
    public List<BookResDto> selectBooks(){
        return bookRepository.findAll().stream()
              //  .map(book -> new BookResDto().toDto(book))
                .map(Book::toDto)
                .collect(Collectors.toList());
    }

    // 3. 책 한건보기
    public BookResDto selectBook(Long id){
        Optional<Book> bookOp = bookRepository.findById(id);

        if(bookOp.isPresent()){
            Book bookPs = bookOp.get();
            return bookPs.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책 삭제하기
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // 5. 책 수정하기
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBook(Long id, BookSaveReqDto dto){
        Optional<Book> bookOp = bookRepository.findById(id);
        if(bookOp.isPresent()){
            Book bookPS = bookOp.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }
}

