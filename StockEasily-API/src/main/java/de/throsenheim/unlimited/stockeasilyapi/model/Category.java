package de.throsenheim.unlimited.stockeasilyapi.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class Category {

    private long id;

    @NotEmpty
    @Length(min = 1, max = 30)
    private String name;


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
        this.name = name;
    }

}
