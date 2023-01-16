package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import io.swagger.annotations.ApiModelProperty;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateArticleResponseDto {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateArticleResponseDto.class);

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

    @JsonIgnore
    private boolean isImageInvalid = true;


    public UpdateArticleResponseDto(Article article) {
        setCategory(new CategoryResponseDto(article.getCategory()));
        setId(article.getId());
        setName(article.getName());
        setProperties(article.getProperties());
        setQuantity(article.getQuantity());
        setImage(article.getImage(), this.id);
    }

    public String getImage() {
        return image;
    }

    public void setImage(Blob imageBlob, long articleId) {
        if (imageBlob == null) {
            LOGGER.debug("Article with id " + articleId + " has no image attached");
            return;
        }
        byte[] blobAsBytes;
        try {
            int blobSize = (int) imageBlob.length();
            blobAsBytes = imageBlob.getBytes(1, blobSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (blobAsBytes != null) {
            final String mimeType = guessMimeType(blobAsBytes, articleId);
            LOGGER.trace("Guessed MIME type: " + mimeType);
            if (mimeType != null) {
                this.image = "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(blobAsBytes);
            }
        }

        if (this.image == null) {
            LOGGER.warn("Could not set image correctly. Seems like a data consistency problem");
            return;
        }
        final int maxIndex = Math.min(this.image.length() - 1, 50);
        final String imageFirstChars = this.image.substring(0, maxIndex);
        LOGGER.debug("Blob converted to base64 string: " + imageFirstChars);
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

    public boolean isImageInvalid() {
        return isImageInvalid;
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

    public Article toModel() {
        Article result = new Article();
        result.setId(getId());
        result.setCategory(getCategory().toModel());
        result.setName(getName());
        result.setImage(getImage());
        result.setProperties(getProperties().stream().map(PropertyResponseDto::toModel).collect(Collectors.toList()));
        result.setQuantity(getQuantity());
        return result;
    }

    @Nullable
    private static String guessMimeType(byte[] imageBytes, long articleId) {
        if (imageBytes.length == 0) {
            LOGGER.trace("Article with id " + articleId + " does not have an image attached");
            return null;
        }
        Tika tika = new Tika();
        return tika.detect(imageBytes);
    }
}
