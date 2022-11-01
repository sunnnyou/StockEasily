package de.throsenheim.unlimited.stockeasilyapi.model;

import java.util.List;

public class Article {

    private int id;
    private String name;
    private List<Property> properties;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
