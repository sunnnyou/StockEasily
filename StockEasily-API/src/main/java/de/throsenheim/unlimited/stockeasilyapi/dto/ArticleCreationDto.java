package de.throsenheim.unlimited.stockeasilyapi.dto;

import de.throsenheim.unlimited.stockeasilyapi.model.Property;

import java.util.List;

public class ArticleCreationDto {

    private String name;
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
