package com.tdd.study.web;

import com.tdd.study.service.BookService;
import com.tdd.study.web.dto.response.BookListRespDto;
import com.tdd.study.web.dto.response.BookResDto;
import com.tdd.study.web.dto.request.BookSaveReqDto;
import com.tdd.study.web.dto.response.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookApiController { // 컴포지션 = has 관계


    private final BookService bookService;
    // 1. 책 등록
    //key=value&key=value
    //{"key" : value, "key" : value}
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult){

        // AOP 요청하는게 좋음
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookResDto bookResDto = bookService.insertBook(bookSaveReqDto);

        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookResDto).build(),HttpStatus.CREATED); // 201 = insert
    }

    // 2. 책 목록보기
    @GetMapping("api/v1/book")
    public ResponseEntity<?> getBookList(){
        BookListRespDto bookListRespDto = bookService.selectBooks();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(bookListRespDto).build(),HttpStatus.OK); // 200
    }
    // 3. 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id){
        BookResDto bookResDto = bookService.selectBook(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 한건 보기 성공").body(bookResDto).build(),HttpStatus.OK); // 200
    }
    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 삭제 성공").body(null).build(),HttpStatus.OK); // 200

    }
    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }
        BookResDto bookResDto = bookService.updateBook(id, bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 수정 성공").body(bookResDto).build(),HttpStatus.OK); // 200
    }
}
