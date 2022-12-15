package de.throsenheim.unlimited.stockeasilyapi.dto.request;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class CreateArticleRequestDto {

    @Valid
    private CategoryRequestDto category;

    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor", required = true)
    @NotNull(message = "Article name is mandatory")
    @Size(min = 1, max = 30, message = "Article name must be between 1 and 30 characters")
    private String name;

    @Valid
    private List<PropertyRequestDto> properties;

    // Why zero as minimum? It might be used for memorable value
    // which is a common thing in terms of bookkeeping
    @ApiModelProperty(notes = "Article quantity", example = "2")
    @PositiveOrZero(message = "Quantity must greater than or equal to 0")
    private int quantity = 1;

//    private MultipartFile image; // TODO/**/ validate size

    public CategoryRequestDto getCategory() {
        return category;
    }

//    public MultipartFile getImage() {
//        return image;
//    }

    public String getName() {
        return name;
    }

    public List<PropertyRequestDto> getProperties() {
        return properties;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCategory(CategoryRequestDto category) {
        this.category = category;
    }

//    public void setImage(MultipartFile image) {
//        this.image = image;
//    }

    public void setName(String name) {
        this.name = (name != null ? name.trim() : null);
    }

    public void setProperties(List<PropertyRequestDto> properties) {
        this.properties = properties;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
