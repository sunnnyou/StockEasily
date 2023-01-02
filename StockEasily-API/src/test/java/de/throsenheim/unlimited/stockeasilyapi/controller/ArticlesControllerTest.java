package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import de.throsenheim.unlimited.stockeasilyapi.service.article.ArticleService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@WebMvcTest(ArticlesController.class)
@SpringBootTest
public class ArticlesControllerTest {
    @MockBean
    private ArticleService articleService;

    @Autowired
    private ArticlesController articlesController;

    @Captor
    private ArgumentCaptor<CreateArticleRequestDto> requestCaptor;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreateArticleSuccess() {
        // Mock the articleService.create method to return a valid CreateArticleResponseDto object
        CreateArticleResponseDto mockResponse = new CreateArticleResponseDto();
        when(articleService.create(any(CreateArticleRequestDto.class))).thenReturn(mockResponse);

        // Create a valid CreateArticleRequestDto object
        CreateArticleRequestDto request = new CreateArticleRequestDto();
        // Set request fields

        // Call the createArticle function
        ResponseEntity<CreateArticleResponseDto> result = articlesController.createArticle(request, null);

        // Verify that the articleService.create method was called with the correct request object
        verify(articleService).create(requestCaptor.capture());
        assertEquals(request, requestCaptor.getValue());

        // Verify that the response is a ResponseEntity with a HttpStatus of CREATED and a CreateArticleResponseDto body
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

//    @Test
//    public void testCreateArticleInvalidRequest() throws Exception {
//        // Create an invalid CreateArticleRequestDto object
//        CreateArticleRequestDto request = new CreateArticleRequestDto();
//        // Set request fields to invalid values
//
//        // Call the createArticle function
//        ResponseEntity<CreateArticleResponseDto> result = articlesController.createArticle(request, null);
//
//
//        // Verify that the response is a ResponseEntity with a HttpStatus of BAD_REQUEST and an ApiErrorDto body
//        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
////        assertTrue(result.getBody() instanceof ApiErrorDto);
//    }

    @Test
    public void testCreateArticleInternalError() throws Exception {
        // Mock the articleService.create method to return null
        when(articleService.create(any(CreateArticleRequestDto.class))).thenReturn(null);

        // Create a valid CreateArticleRequestDto object
        CreateArticleRequestDto request = new CreateArticleRequestDto();
        // Set request fields

        // Call the createArticle function
        ResponseEntity<CreateArticleResponseDto> result = articlesController.createArticle(request, null);

        // Verify that the articleService.create method was called with the correct request object
        verify(articleService).create(requestCaptor.capture());
        assertEquals(request, requestCaptor.getValue());

        // Verify that the response is a ResponseEntity with a HttpStatus of INTERNAL_SERVER_ERROR and a null body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testCreateArticleInvalidImage() throws Exception {
        // Mock the articleService.create method to return a CreateArticleResponseDto object with an invalid image
        CreateArticleResponseDto mockResponse = new CreateArticleResponseDto();
        mockResponse.setImageInvalid(true);
        when(articleService.create(any(CreateArticleRequestDto.class))).thenReturn(mockResponse);
        when(articleService.getImageFieldError()).thenReturn(new FieldError("image", "invalid", "Invalid image"));

        // Create a valid CreateArticleRequestDto object with an image
        CreateArticleRequestDto request = new CreateArticleRequestDto();
        request.setImage("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAA");
        // Set other request fields

        // Call the createArticle function
        AtomicReference<ResponseEntity<CreateArticleResponseDto>> result = new AtomicReference<>();
        assertThrows(InvalidBodyException.class, () -> {
            result.set(articlesController.createArticle(request, null));
        });

        // Verify that the articleService.create method was called with the correct request object
        verify(articleService).create(requestCaptor.capture());
        assertEquals(request, requestCaptor.getValue());

        assertNull(result.get());
//        assertTrue(result.getBody() instanceof ApiErrorDto);
    }

}