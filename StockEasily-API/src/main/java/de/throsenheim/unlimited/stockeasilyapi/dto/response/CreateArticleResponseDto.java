package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CreateArticleResponseDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateArticleResponseDto.class);

    private CategoryResponseDto category;

    @ApiModelProperty(notes = "Article id", example = "123")
    private long id;

    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor")
    private String name;

    @ApiModelProperty(notes = "Article properties")
    private List<PropertyResponseDto> properties;

    @ApiModelProperty(notes = "Article quantity", example = "2")
    private int quantity;

    public CreateArticleResponseDto(Article article) {
        if (article == null) {
            LOGGER.error("Could not initialize CreateArticleResponseDto, article is null");
            return;
        }

        setCategory(new CategoryResponseDto(article.getCategory()));
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

}
