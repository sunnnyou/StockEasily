package de.throsenheim.unlimited.stockeasilyapi.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageSizeValidator.class)
public @interface ImageSize {
    String message() default "Image is too big";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    long min() default 0;

    @Value("${app.image.size.max}")
    long max() default Long.MAX_VALUE;
}
