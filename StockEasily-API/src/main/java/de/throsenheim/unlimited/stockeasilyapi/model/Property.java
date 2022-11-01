package de.throsenheim.unlimited.stockeasilyapi.model;

public class Property {

    private int id; // TODO use for database

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
