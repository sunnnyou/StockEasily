package de.throsenheim.unlimited.stockeasilyapi.model;

import javax.validation.constraints.NotEmpty;

public class Property {

    private int id;

    @NotEmpty
    private String name;

    private String description;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
