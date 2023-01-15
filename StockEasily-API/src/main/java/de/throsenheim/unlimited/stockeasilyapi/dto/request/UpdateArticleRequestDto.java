package de.throsenheim.unlimited.stockeasilyapi.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateArticleRequestDto {

    @ApiModelProperty(notes = "Article category", example = "Cables", required = true)
    @Valid
    private CategoryRequestDto category;

    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor", required = true)
    @Size(min = 1, max = 30, message = "Article name must be between 1 and 30 characters")
    private String name;

    @Valid
    private List<PropertyRequestDto> properties;

    // Why zero as minimum? It might be used for memorable value
    // which is a common thing in terms of bookkeeping
    @ApiModelProperty(notes = "Article quantity", example = "2")
    @PositiveOrZero(message = "Quantity must greater than or equal to 0")
    private int quantity = -1;

    private String image;

    public CategoryRequestDto getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}