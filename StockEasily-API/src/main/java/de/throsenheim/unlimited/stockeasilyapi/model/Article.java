package de.throsenheim.unlimited.stockeasilyapi.model;

import java.sql.Blob;
import java.util.List;

public class Article {

    private long id;
    private String name;
    private Category category;
    private List<Property> properties;
    private int quantity;
    private Blob image;

    public Category getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public Blob getImage() {
        return this.image;
    }

    public String getName() {
        return name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
