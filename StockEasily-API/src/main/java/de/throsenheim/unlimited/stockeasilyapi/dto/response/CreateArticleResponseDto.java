package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.stream.Collectors;

public class CreateArticleResponseDto {

    private CategoryResponseDto category;

    private long id;

    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor", required = true)
    private String name;

    private List<PropertyResponseDto> properties;

    private int quantity;

    public CreateArticleResponseDto(Article article) {
        if (article == null) {
            System.out.println("Could not initialize CreateArticleResponseDto, article is null");
            return;
        }

        setCategory(article.getCategory());
        setId(article.getId());
        setName(article.getName());
        setProperties(article.getProperties());
        setQuantity(article.getQuantity());
    }

    public CategoryResponseDto getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PropertyResponseDto> getProperties() {
        return properties;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCategory(CategoryResponseDto category) {
        this.category = category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties.stream().map(PropertyResponseDto::new).collect(Collectors.toList());
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void setCategory(Category category) {
        this.category = new CategoryResponseDto(category);
    }

}
