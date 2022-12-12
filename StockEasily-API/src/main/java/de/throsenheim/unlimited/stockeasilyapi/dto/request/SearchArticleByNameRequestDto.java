package de.throsenheim.unlimited.stockeasilyapi.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchArticleByNameRequestDto {
    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor", required = true)
    @NotNull(message = "Name is mandatory")
    @Size(min = 1, max = 30, message = "Article name must be between 1 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
