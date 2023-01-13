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
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ArticlesControllerTest {
    private static final String BASE_64_IMAGE = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAsICAoIBwsKCQoNDAsNERwSEQ8PESIZGhQcKSQrKigkJyctMkA3LTA9MCcnOEw5PUNFSElIKzZPVU5GVEBHSEX/2wBDAQwNDREPESESEiFFLicuRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUX/wAARCACKAJgDASIAAhEBAxEB/8QAGgABAQEBAQEBAAAAAAAAAAAAAAQDAgEFB//EACsQAQABAQQIBwEBAAAAAAAAAAABAgMEEZESExQhMVFScTIzQUJigaFyYf/EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD9PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAc6yjrpzZXqqYimmJwiccXFN10qYmasMYx4Ao1lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8ADZPn+A31lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8Nk+f4CiKoq8MxPZ6jmKrvaxv3cd3rCwAAAAAAAAE979n23o8FPaGF79n23o8FPaAegVTFNMzPCN4GMY4YxjPoIK65qrmrhKyytNZRj6+sA7AAABJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejwU9oB6mvNpjOhHpvlvaV6uiasoQTOM4zxkB3ZWmrrx9J4uAH0YnGMY3wyrvFNFejhjz/AMc6UWFjhFWMzw3pZnGcQfRjfGMcBPdrTGNCfTgoBJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejy6e0AkvFpp14RwjcyV7LRzqzg2WjnVnAJBXstHOrODZaOdWcAkFey0c6s4Nlo51ZwCWJmJiY4wus64tKIqhnstHOrOHdnZxZxOjjOPME968yP5VpL15sdlYAAAAAAAAM7ay1tMYTvjgxii8UxhGMR3hUAl0bxznODRvHOc4VAJdG8c5zg0bxznOFQCXRvHOc4NG8c5zhUAl0bxznODRvHOc4VAJrOwrm00rTdhOPdSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z";
    private static final String BASE_64_RESPONSE = "/9j/4AAQSkZJRgABAQAAAQABAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAsICAoIBwsKCQoNDAsNERwSEQ8PESIZGhQcKSQrKigkJyctMkA3LTA9MCcnOEw5PUNFSElIKzZPVU5GVEBHSEX/2wBDAQwNDREPESESEiFFLicuRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUVFRUX/wAARCACKAJgDASIAAhEBAxEB/8QAGgABAQEBAQEBAAAAAAAAAAAAAAQDAgEFB//EACsQAQABAQQIBwEBAAAAAAAAAAABAgMEEZESExQhMVFScTIzQUJigaFyYf/EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD9PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAc6yjrpzZXqqYimmJwiccXFN10qYmasMYx4Ao1lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8ADZPn+A31lHXTmayjrpzYbJ8/w2T5/gN9ZR105mso66c2GyfP8Nk+f4CiKoq8MxPZ6jmKrvaxv3cd3rCwAAAAAAAAE979n23o8FPaGF79n23o8FPaAegVTFNMzPCN4GMY4YxjPoIK65qrmrhKyytNZRj6+sA7AAABJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejwU9oB6mvNpjOhHpvlvaV6uiasoQTOM4zxkB3ZWmrrx9J4uAH0YnGMY3wyrvFNFejhjz/AMc6UWFjhFWMzw3pZnGcQfRjfGMcBPdrTGNCfTgoBJevMj+VaS9eZH8qwAAAAAAAAT3v2fbejwU9oYXv2fbejy6e0AkvFpp14RwjcyV7LRzqzg2WjnVnAJBXstHOrODZaOdWcAkFey0c6s4Nlo51ZwCWJmJiY4wus64tKIqhnstHOrOHdnZxZxOjjOPME968yP5VpL15sdlYAAAAAAAAM7ay1tMYTvjgxii8UxhGMR3hUAl0bxznODRvHOc4VAJdG8c5zg0bxznOFQCXRvHOc4NG8c5zhUAl0bxznODRvHOc4VAJrOwrm00rTdhOPdSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z";
    private ArticleRepository articleRepository;
    private ArticlesController articlesController;
    private BindingResult bindingResult;

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
        byte[] decodedBytes;
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

    @Test
    void searchAllArticlesTest() throws SQLException {
        Article article = new Article();
        Article article2 = new Article();

        // Category
        Category category = new Category();
        category.setName("Category2");
        article.setCategory(category);
        Category category2 = new Category();
        category2.setName("Category2");
        article2.setCategory(category2);

        // Name
        article.setName("Name");
        article2.setName("Name2");

        // Properties
        Property prop1 = new Property();
        prop1.setDescription("Description");
        prop1.setName("PropName");
        Property prop2 = new Property();
        prop2.setDescription("Description2");
        prop2.setName("PropName2");
        article.setProperties(List.of(prop1, prop2));
        article2.setProperties(List.of(prop2, prop1));

        // Quantity
        article.setQuantity(25);
        article2.setQuantity(125);

        // Image
        String image = BASE_64_IMAGE;
        byte[] decodedBytes;
        image = image.substring(image.indexOf(',') + 1);
        decodedBytes = Base64.getDecoder().decode(image);
        article.setImage(new SerialBlob(decodedBytes));
        article2.setImage(new SerialBlob(decodedBytes));

        when(articleRepository.findAll()).thenReturn(List.of(article, article2));

        ResponseEntity<List<SearchArticleResponse>> result = articlesController.searchAllArticles();
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Testing Body which is an article dto
        List<SearchArticleResponse> resultArticleList = result.getBody();
        assert resultArticleList != null;
        assertEquals(2, resultArticleList.size());
        assertEquals(resultArticleList.get(0).getName(), article.getName());
        assertEquals(resultArticleList.get(1).getName(), article2.getName());
        assertEquals(resultArticleList.get(0).getCategory().getName(), article.getCategory().getName());
        assertEquals(resultArticleList.get(1).getCategory().getName(), article2.getCategory().getName());
        assertEquals(resultArticleList.get(0).getProperties().size(), article.getProperties().size());
        assertEquals(resultArticleList.get(1).getProperties().size(), article2.getProperties().size());
        assertEquals(resultArticleList.get(0).getQuantity(), article.getQuantity());
        assertEquals(resultArticleList.get(1).getQuantity(), article2.getQuantity());
        assertEquals(BASE_64_RESPONSE, resultArticleList.get(0).getImage());
        assertEquals(BASE_64_RESPONSE, resultArticleList.get(1).getImage());
        // id 0 because it's created in runtime without database
        assertEquals(0, resultArticleList.get(1).getId());
        assertEquals(0, resultArticleList.get(1).getId());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void searchAllArticlesPageTest() throws SQLException {
        Article article = new Article();
        Article article2 = new Article();

        // Category
        Category category = new Category();
        category.setName("Category2");
        article.setCategory(category);
        Category category2 = new Category();
        category2.setName("Category2");
        article2.setCategory(category2);

        // Name
        article.setName("Name");
        article2.setName("Name2");

        // Properties
        Property prop1 = new Property();
        prop1.setDescription("Description");
        prop1.setName("PropName");
        Property prop2 = new Property();
        prop2.setDescription("Description2");
        prop2.setName("PropName2");
        article.setProperties(List.of(prop1, prop2));
        article2.setProperties(List.of(prop2, prop1));

        // Quantity
        article.setQuantity(25);
        article2.setQuantity(125);

        // Image
        String image = BASE_64_IMAGE;
        byte[] decodedBytes;
        image = image.substring(image.indexOf(',') + 1);
        decodedBytes = Base64.getDecoder().decode(image);
        article.setImage(new SerialBlob(decodedBytes));
        article2.setImage(new SerialBlob(decodedBytes));

        when(articleRepository.findAllPage(10, 1)).thenReturn(List.of(article, article2));

        ResponseEntity<List<SearchArticleResponse>> result = articlesController.searchAllArticlesPage(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Testing Body which is an article dto
        List<SearchArticleResponse> resultArticleList = result.getBody();
        assert resultArticleList != null;
        assertEquals(2, resultArticleList.size());
        assertEquals(resultArticleList.get(0).getName(), article.getName());
        assertEquals(resultArticleList.get(1).getName(), article2.getName());
        assertEquals(resultArticleList.get(0).getCategory().getName(), article.getCategory().getName());
        assertEquals(resultArticleList.get(1).getCategory().getName(), article2.getCategory().getName());
        assertEquals(resultArticleList.get(0).getProperties().size(), article.getProperties().size());
        assertEquals(resultArticleList.get(1).getProperties().size(), article2.getProperties().size());
        assertEquals(resultArticleList.get(0).getQuantity(), article.getQuantity());
        assertEquals(resultArticleList.get(1).getQuantity(), article2.getQuantity());
        assertEquals(BASE_64_RESPONSE, resultArticleList.get(0).getImage());
        assertEquals(BASE_64_RESPONSE, resultArticleList.get(1).getImage());
        // id 0 because it's created in runtime without database
        assertEquals(0, resultArticleList.get(1).getId());
        assertEquals(0, resultArticleList.get(1).getId());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).findAllPage(10, 1);
    }

    @Test
    void searchAllArticlesPageTestBadRequest() {
        ResponseEntity<List<SearchArticleResponse>> result = articlesController.searchAllArticlesPage(0);
        ResponseEntity<List<SearchArticleResponse>> result2 = articlesController.searchAllArticlesPage(-1);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, result2.getStatusCode());

        assertNull(result.getBody());
        assertNull(result2.getBody());

        assertEquals("[]", result.getHeaders().toString());
        assertEquals("[]", result2.getHeaders().toString());

        verify(articleRepository, times(0)).findAllPage(anyInt(), anyInt());
    }

    @Test
    void getArticleRepositorySizeTest() {
        int size = 5;

        when(articleRepository.getSize()).thenReturn(size);

        ResponseEntity<Integer> result = articlesController.getArticleRepositorySize();

        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(size, result.getBody());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).getSize();
    }

    @Test
    void getArticleRepositorySizeQueryTest() {
        int size = 5;
        String query = "something";

        when(articleRepository.getSizeQuery(query)).thenReturn(size);

        ResponseEntity<Integer> result = articlesController.getArticleRepositorySizeQuery(query);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        assertEquals(size, result.getBody());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).getSizeQuery(query);
    }

    @Test
    void searchFromQueryTest() throws SQLException {
        String query = "something";
        int page = 1;

        Article article = new Article();
        Article article2 = new Article();

        // Category
        Category category = new Category();
        category.setName("Category2");
        article.setCategory(category);
        Category category2 = new Category();
        category2.setName("Category2");
        article2.setCategory(category2);

        // Name
        article.setName("Name");
        article2.setName("Name2");

        // Properties
        Property prop1 = new Property();
        prop1.setDescription("Description");
        prop1.setName("PropName");
        Property prop2 = new Property();
        prop2.setDescription("Description2");
        prop2.setName("PropName2");
        article.setProperties(List.of(prop1, prop2));
        article2.setProperties(List.of(prop2, prop1));

        // Quantity
        article.setQuantity(25);
        article2.setQuantity(125);

        // Image
        String image = BASE_64_IMAGE;
        byte[] decodedBytes;
        image = image.substring(image.indexOf(',') + 1);
        decodedBytes = Base64.getDecoder().decode(image);
        article.setImage(new SerialBlob(decodedBytes));
        article2.setImage(new SerialBlob(decodedBytes));

        when(articleRepository.findAllByQuery(query, 10, page)).thenReturn(List.of(article, article2));

        ResponseEntity<List<SearchArticleResponse>> result = articlesController.searchFromQuery(query, page);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Testing Body which is an article dto
        List<SearchArticleResponse> resultArticleList = result.getBody();
        assert resultArticleList != null;
        assertEquals(2, resultArticleList.size());
        assertEquals(resultArticleList.get(0).getName(), article.getName());
        assertEquals(resultArticleList.get(1).getName(), article2.getName());
        assertEquals(resultArticleList.get(0).getCategory().getName(), article.getCategory().getName());
        assertEquals(resultArticleList.get(1).getCategory().getName(), article2.getCategory().getName());
        assertEquals(resultArticleList.get(0).getProperties().size(), article.getProperties().size());
        assertEquals(resultArticleList.get(1).getProperties().size(), article2.getProperties().size());
        assertEquals(resultArticleList.get(0).getQuantity(), article.getQuantity());
        assertEquals(resultArticleList.get(1).getQuantity(), article2.getQuantity());
        assertEquals(BASE_64_RESPONSE, resultArticleList.get(0).getImage());
        assertEquals(BASE_64_RESPONSE, resultArticleList.get(1).getImage());
        // id 0 because it's created in runtime without database
        assertEquals(0, resultArticleList.get(1).getId());
        assertEquals(0, resultArticleList.get(1).getId());

        assertEquals("[]", result.getHeaders().toString());

        verify(articleRepository, times(1)).findAllByQuery(query, 10, page);
    }

    @Test
    void searchFromQueryTestBadRequest() {
        ResponseEntity<List<SearchArticleResponse>> result = articlesController.searchFromQuery("something", 0);
        ResponseEntity<List<SearchArticleResponse>> result2 = articlesController.searchFromQuery("something", -1);
        ResponseEntity<List<SearchArticleResponse>> result3 = articlesController.searchFromQuery("   ", 1);
        ResponseEntity<List<SearchArticleResponse>> result4 = articlesController.searchFromQuery(null, 1);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, result2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, result3.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, result4.getStatusCode());

        assertNull(result.getBody());
        assertNull(result2.getBody());
        assertNull(result3.getBody());
        assertNull(result4.getBody());

        assertEquals("[]", result.getHeaders().toString());
        assertEquals("[]", result2.getHeaders().toString());
        assertEquals("[]", result3.getHeaders().toString());
        assertEquals("[]", result4.getHeaders().toString());

        verify(articleRepository, times(0)).findAllByQuery(anyString(), anyInt(), anyInt());
    }
}