package de.throsenheim.unlimited.stockeasilyapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryRequestDto {
    @NotEmpty(message = "Category name must not be null or empty")
    @Size(min = 1, max = 30, message = "Category name must be between 1 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
