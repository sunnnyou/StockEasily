package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import de.throsenheim.unlimited.stockeasilyapi.model.User;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserResponseDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResponseDto.class);

    @ApiModelProperty(notes = "User id", example = "123")
    private long id;

    @ApiModelProperty(notes = "Email Address", example = "example@example.com")
    private String emailAddress;

    @ApiModelProperty(notes = "Password", example = "P455w0rd")
    private String password;

    @ApiModelProperty(notes = "Does user want to be notified of article changes?", example = "true")
    private boolean isNotified;

//    @ApiModelProperty(notes = "User articles")
//    // TODO use EditArticleResponseDto
//    private List<CreateArticleResponseDto> articles;

    public UserResponseDto(User user) {
        if (user == null) {
            LOGGER.error("Could not initialize EditUserResponseDto, user is null");
            return;
        }

        setId(user.getId());
        setEmailAddress(user.getEmailAddress());
        setPassword(user.getPassword());
        setNotified(user.isNotified());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

//    public List<CreateArticleResponseDto> getArticles() {
//        return articles;
//    }
//
//    public void setArticles(List<Article> articles) {
//        this.articles = articles.stream().map(CreateArticleResponseDto::new).collect(Collectors.toList());
//    }
}
