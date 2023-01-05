package de.throsenheim.unlimited.stockeasilyapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;

public class ImageSizeValidator implements ConstraintValidator<ImageSize, String> {

    private long min;
    private long max;

    @Override
    public void initialize(ImageSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String image, ConstraintValidatorContext context) {
        if (image == null) {
            return true;
        }
        try {
            // Decode the base64 encoded image and get its size in bytes
            byte[] imageBytes = Base64.getDecoder().decode(image);
            long imageSize = imageBytes.length;
            // Check if the image size is within the specified range
            return imageSize >= min && imageSize <= max;
        } catch (IllegalArgumentException e) {
            // Return false if the image is not a valid base64 encoded string
            return false;
        }
    }
}