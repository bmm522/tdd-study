package com.tdd.study.service;

import com.tdd.study.domain.Book;
import com.tdd.study.domain.BookRepository;
import com.tdd.study.web.dto.BookResDto;
import com.tdd.study.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto insertBook(BookSaveReqDto dto){
        Book bookPs = bookRepository.save(dto.toEntity());
        return new BookResDto().toDto(bookPs);
    }

    // 2. 책 목록보기
    public List<BookResDto> selectBooks(){
        return bookRepository.findAll().stream()
                .map(new BookResDto()::toDto)
                .collect(Collectors.toList());
    }
}
