package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CategoryRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.PropertyRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.SearchArticleResponse;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import de.throsenheim.unlimited.stockeasilyapi.service.article.ArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.sql.rowset.serial.SerialBlob;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ArticlesControllerTest {
    private ArticleRepository articleRepository;
    private ArticlesController articlesController;
    private BindingResult bindingResult;
    private static final String BASE_64_IMAGE = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAsICAoIBwsKCQoNDAsNERwSEQ8PESIZGhQcKSQrKigkJyctMkA3LTA9MCcnOEw5PUNFSElIKzZPVU5GVEBHSEX/2wBDAQwNDREPESESEiFFLicuRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUX/wAARCACKAJgDASIAAhEBAxEB/8QAGgABAQEBAQEBAAAAAAAAAAAAAAQDAgEFB//EACsQAQABAQQIBwEBAAAAAAAAAAABAgMEEZESExQhMVFScTIzQUJigaFyYf/EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD9PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAc6yjrpzZXqqYimmJwiccXFN10qYmasMYx4Ao1lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8ADZPn+A31lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8Nk+f4CiKoq8MxPZ6jmKrvaxv3cd3rCwAAAAAAAAE979n23o8FPaGF79n23o8FPaAegVTFNMzPCN4GMY4YxjPoIK65qrmrhKyytNZRj6+sA7AAABJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejwU9oB6mvNpjOhHpvlvaV6uiasoQTOM4zxkB3ZWmrrx9J4uAH0YnGMY3wyrvFNFejhjz/AMc6UWFjhFWMzw3pZnGcQfRjfGMcBPdrTGNCfTgoBJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejy6e0AkvFpp14RwjcyV7LRzqzg2WjnVnAJBXstHOrODZaOdWcAkFey0c6s4Nlo51ZwCWJmJiY4wus64tKIqhnstHOrOHdnZxZxOjjOPME968yP5VpL15sdlYAAAAAAAAM7ay1tMYTvjgxii8UxhGMR3hUAl0bxznODRvHOc4VAJdG8c5zg0bxznOFQCXRvHOc4NG8c5zhUAl0bxznODRvHOc4VAJrOwrm00rTdhOPdSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z";
    private static final String BASE_64_RESPONSE = "/9j/4AAQSkZJRgABAQAAAQABAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAsICAoIBwsKCQoNDAsNERwSEQ8PESIZGhQcKSQrKigkJyctMkA3LTA9MCcnOEw5PUNFSElIKzZPVU5GVEBHSEX/2wBDAQwNDREPESESEiFFLicuRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUX/wAARCACKAJgDASIAAhEBAxEB/8QAGgABAQEBAQEBAAAAAAAAAAAAAAQDAgEFB//EACsQAQABAQQIBwEBAAAAAAAAAAABAgMEEZESExQhMVFScTIzQUJigaFyYf/EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD9PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAc6yjrpzZXqqYimmJwiccXFN10qYmasMYx4Ao1lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8ADZPn+A31lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8Nk+f4CiKoq8MxPZ6jmKrvaxv3cd3rCwAAAAAAAAE979n23o8FPaGF79n23o8FPaAegVTFNMzPCN4GMY4YxjPoIK65qrmrhKyytNZRj6+sA7AAABJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejwU9oB6mvNpjOhHpvlvaV6uiasoQTOM4zxkB3ZWmrrx9J4uAH0YnGMY3wyrvFNFejhjz/AMc6UWFjhFWMzw3pZnGcQfRjfGMcBPdrTGNCfTgoBJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejy6e0AkvFpp14RwjcyV7LRzqzg2WjnVnAJBXstHOrODZaOdWcAkFey0c6s4Nlo51ZwCWJmJiY4wus64tKIqhnstHOrOHdnZxZxOjjOPME968yP5VpL15sdlYAAAAAAAAM7ay1tMYTvjgxii8UxhGMR3hUAl0bxznODRvHOc4VAJdG8c5zg0bxznOFQCXRvHOc4NG8c5zhUAl0bxznODRvHOc4VAJrOwrm00rTdhOPdSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z";

    @BeforeEach
    void ControllerSetup() {
        articleRepository = mock(ArticleRepository.class);
        ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepository, 524288);
        articlesController = new ArticlesController(articleService);
        bindingResult = mock(BindingResult.class);
    }

    @Test
    void createArticleTest() {
        CreateArticleRequestDto articleNew = new CreateArticleRequestDto();

        // Category
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Category");
        articleNew.setCategory(categoryRequestDto);

        // Name
        articleNew.setName("Name");

        // Properties
        PropertyRequestDto prop1 = new PropertyRequestDto();
        prop1.setDescription("Description");
        prop1.setName("PropName");
        PropertyRequestDto prop2 = new PropertyRequestDto();
        prop2.setDescription("Description2");
        prop2.setName("PropName2");
        articleNew.setProperties(List.of(prop1, prop2));

        // Quantity
        articleNew.setQuantity(25);

        // Image
        articleNew.setImage(BASE_64_IMAGE);

        Article articleResult = new Article(articleNew, null);

        when(articleRepository.save(any(Article.class))).thenReturn(articleResult);

        ResponseEntity<CreateArticleResponseDto> result = articlesController.createArticle(articleNew, bindingResult);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Testing Body which is an article dto
        CreateArticleResponseDto resultArticle = result.getBody();
        assert resultArticle != null;
        assertEquals(resultArticle.getName(), articleNew.getName());
        assertEquals(resultArticle.getCategory().getName(), articleNew.getCategory().getName());
        assertEquals(resultArticle.getProperties().size(), articleNew.getProperties().size());
        assertEquals(resultArticle.getQuantity(), articleNew.getQuantity());
        // id 0 because it's created in runtime without database
        assertEquals(0, resultArticle.getId());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void searchArticleTest() throws SQLException {
        Article article = new Article();

        // Category
        Category category = new Category();
        category.setName("Category");
        article.setCategory(category);

        // Name
        article.setName("Name");

        // Properties
        Property prop1 = new Property();
        prop1.setDescription("Description");
        prop1.setName("PropName");
        Property prop2 = new Property();
        prop2.setDescription("Description2");
        prop2.setName("PropName2");
        article.setProperties(List.of(prop1, prop2));

        // Quantity
        article.setQuantity(25);

        // Image
        String image = BASE_64_IMAGE;
        byte[] decodedBytes = null;
            image = image.substring(image.indexOf(',') + 1);
            decodedBytes = Base64.getDecoder().decode(image);

        article.setImage(new SerialBlob(decodedBytes));

        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));

        ResponseEntity<SearchArticleResponse> result = articlesController.searchArticle("1");
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Testing Body which is an article dto
        SearchArticleResponse resultArticle = result.getBody();
        assert resultArticle != null;
        assertEquals(resultArticle.getName(), article.getName());
        assertEquals(resultArticle.getCategory().getName(), article.getCategory().getName());
        assertEquals(resultArticle.getProperties().size(), article.getProperties().size());
        assertEquals(resultArticle.getQuantity(), article.getQuantity());
        assertEquals(BASE_64_RESPONSE, resultArticle.getImage());
        // id 0 because it's created in runtime without database
        assertEquals(0, resultArticle.getId());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).findById(anyLong());
    }

    @Test
    void searchArticleTestNotFound() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<SearchArticleResponse> result = articlesController.searchArticle("1");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        assertNull(result.getBody());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).findById(anyLong());
    }
}