package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.Property;

public class PropertyResponseDto {

    private long id;

    private String name;

    private String description;

    public PropertyResponseDto() {
    }

    public PropertyResponseDto(Property property) {
        setId(property.getId());
        setDescription(property.getDescription());
        setName(property.getName());
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
