package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import io.swagger.annotations.ApiModelProperty;

public class PropertyResponseDto {

    @ApiModelProperty(notes = "Property ID", example = "78")
    private long id;

    @ApiModelProperty(notes = "Property name", example = "Screen diagonal")
    private String name;

    @ApiModelProperty(notes = "Property description", example = "30\"")
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

    public Property toModel() {
        Property result = new Property();
        result.setId(getId());
        result.setName(getName());
        result.setDescription(getDescription());
        return result;
    }

}
