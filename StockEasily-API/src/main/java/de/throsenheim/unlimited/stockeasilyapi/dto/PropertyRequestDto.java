package de.throsenheim.unlimited.stockeasilyapi.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PropertyRequestDto {

    @Size(max = 50, message = "Property description must be less than 50 characters")
    private String description;

    @NotNull(message = "Property name is mandatory")
    @Size(min = 1, max = 30, message = "Property name must be between 1 and 30 characters")
    private String name;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

}
