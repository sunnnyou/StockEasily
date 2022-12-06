package de.throsenheim.unlimited.stockeasilyapi.model;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.UserRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public class User {
    private long id;
    private String emailAddress;
    private String password;
    private boolean isNotified;
    private LocalDateTime loginDate;
    private List<Article> articles;

    public User() {
    }

    public User(UserRequestDto request) {
        setEmailAddress(request.getEmailAddress());
        setPassword(request.getPassword());
        setNotified(request.isNotified());
        setArticles(Article.getArticles(request.getArticles()));
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

    public LocalDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
