package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SearchArticleResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchArticleResponse.class);

    private CategoryResponseDto category;

    @ApiModelProperty(notes = "Article id", example = "123")
    private long id;

    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor")
    private String name;

    @ApiModelProperty(notes = "Article properties")
    private List<PropertyResponseDto> properties;

    @ApiModelProperty(notes = "Article quantity", example = "2")
    private int quantity;

    @ApiModelProperty(notes = "Base64 image string")
    private String image;

    public SearchArticleResponse(Article article) {
        if (article == null) {
            LOGGER.error("Could not initialize CreateArticleResponseDto, article is null");
            return;
        }

        setCategory(new CategoryResponseDto(article.getCategory()));
        setId(article.getId());
        setName(article.getName());
        setProperties(article.getProperties());
        setQuantity(article.getQuantity());
        try {
            LOGGER.info("Set image of article with id {}", article.getId());
            setImage(article.getImage());
            LOGGER.info("Base64 String set: {}", this.image != null);
        } catch (SQLException e) {
            LOGGER.error("Could not set encode image to base64. ", e);
            setImageBase64Null();
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(Blob imageBlob) throws SQLException {
        if (imageBlob != null) {
            int blobLength = (int) imageBlob.length();
            byte[] blobAsBytes = imageBlob.getBytes(1, blobLength);
            this.image = Base64.getEncoder().encodeToString(blobAsBytes);
        } else {
            this.image = null;
        }
    }

    public void setImageBase64Null() {
        this.image = null;
    }

    public CategoryResponseDto getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDto category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyResponseDto> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties.stream().map(PropertyResponseDto::new).collect(Collectors.toList());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
