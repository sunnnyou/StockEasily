package de.throsenheim.unlimited.stockeasilyapi.model;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CategoryRequestDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Category {

    private long id = 0;

    @NotEmpty(message = "Category name must not be null or empty")
    @Size(min = 1, max = 30, message = "Category name must be between 1 and 30 characters")
    private String name;

    public Category() {

    }

    public Category(CategoryRequestDto categoryRequest) {
        setName(categoryRequest.getName());
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
        this.name = (name != null ? name.trim() : null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Property other)) {
            return false;
        }
        return this.getName().equals(other.getName());
    }
}
