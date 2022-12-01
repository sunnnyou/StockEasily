package de.throsenheim.unlimited.stockeasilyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class Article {

    private long id;
    private String name;
    private Category category;
    private List<Property> properties;
    private int quantity;
    private Blob image;

    public Article() {

    }

    public Article(CreateArticleRequestDto request) {
        setName(request.getName());
        setImage(request.getImage());
        setQuantity(request.getQuantity());
        setCategory(new Category(request.getCategory()));
        setProperties(Property.getProperties(request.getProperties()));
    }

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
        this.name = (name != null ? name.trim() : null);
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void setImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                setImage(new SerialBlob(file.getBytes()));
                System.out.println("Initialized image with new SerialBlob of length " + getImage().length());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
