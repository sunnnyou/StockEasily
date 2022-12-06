package de.throsenheim.unlimited.stockeasilyapi.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRequestDto {

    @ApiModelProperty(notes = "Email Address", example = "example@example.com")
    @Size(min = 1, max = 50, message = "Users email address must be between 1 and 50 characters")
    @Email(message = "Email address is malformed")
    private String emailAddress;

    // Is always 64 characters long if made to Base64 in front end
    @ApiModelProperty(notes = "Password", example = "P455w0rd")
    private String password;

    @ApiModelProperty(notes = "Does user want to be notified of article changes?", example = "true")
    private boolean isNotified;

    @Valid
    private List<CreateArticleRequestDto> articles;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public List<CreateArticleRequestDto> getArticles() {
        return articles;
    }

    public void setArticles(List<CreateArticleRequestDto> articles) {
        this.articles = articles;
    }
}
