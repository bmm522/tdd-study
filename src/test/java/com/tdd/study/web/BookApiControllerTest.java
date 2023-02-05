package com.tdd.study.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.tdd.study.service.BookService;
import com.tdd.study.web.dto.request.BookSaveReqDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.assertj.core.api.Assertions.*;
// 통합테스트 (C, S, R)
// 컨트롤러만 테스트하는 것이 아님

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate rt;
    private static HttpHeaders headers;

    private static ObjectMapper om;
    @BeforeAll
    public static void init(){
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("세이브 테스트")
    public void saveBookTest() throws Exception {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("스프링1강");
        bookSaveReqDto.setAuthor("김지인");

        String body = om.writeValueAsString(bookSaveReqDto);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        // when

        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo("스프링1강");
    }
    
}
