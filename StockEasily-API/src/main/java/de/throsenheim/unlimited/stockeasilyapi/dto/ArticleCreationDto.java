package de.throsenheim.unlimited.stockeasilyapi.dto;

import de.throsenheim.unlimited.stockeasilyapi.model.Property;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ArticleCreationDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Properties are mandatory")
    private List<Property> properties;

    public String getName() {
        return name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
