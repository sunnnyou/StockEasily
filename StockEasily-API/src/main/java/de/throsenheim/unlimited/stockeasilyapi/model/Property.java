package de.throsenheim.unlimited.stockeasilyapi.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Property {

    private long id;

    @NotNull(message = "Property name is mandatory")
    @Size(min = 1, max = 30, message = "Property name must be between 1 and 30 characters")
    private String name;

    @Size(max = 50, message = "Property description must be less than 50 characters")
    private String description;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = (name != null ? name.trim() : null);
    }

    public void setDescription(String description) {
        this.description = (description != null ? description.trim() : null);
    }

}
