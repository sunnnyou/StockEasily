package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import io.swagger.annotations.ApiModelProperty;

public class CategoryResponseDto {

    @ApiModelProperty(notes = "Category ID", example = "1")
    private long id;

    @ApiModelProperty(notes = "Category name", example = "Monitors")
    private String name;

    public CategoryResponseDto() {
    }

    public CategoryResponseDto(Category category) {
        setId(category.getId());
        setName(category.getName());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
