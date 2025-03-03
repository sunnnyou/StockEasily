package de.throsenheim.unlimited.stockeasilyapi.model;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

public class Article {

    private static final Logger LOGGER = LoggerFactory.getLogger(Article.class);

    private long id = 0;
    private String name;
    private Category category;
    private List<Property> properties;
    private int quantity;
    private Blob image;

    public Article() {
    }

    public Article(CreateArticleRequestDto request, byte[] decodedImage) {
        setName(request.getName());
        setImage(decodedImage);
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

    private void setImage(byte[] decodedImage) {
        if (decodedImage == null) {
            LOGGER.trace("File was not attached, aborting");
            return;
        }

        try {
            setImage(new SerialBlob(decodedImage));
            LOGGER.debug("Initialized image with new SerialBlob of length " + getImage().length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImage(String encodedImage) {
        if (encodedImage == null) {
            setImage((Blob) null);
            return;
        }
        encodedImage = encodedImage.substring(encodedImage.indexOf(',') + 1);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
        setImage(decodedBytes);
    }

}
