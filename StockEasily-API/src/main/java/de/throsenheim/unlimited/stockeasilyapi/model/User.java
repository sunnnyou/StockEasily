package de.throsenheim.unlimited.stockeasilyapi.model;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.UserRequestDto;

import java.sql.Timestamp;
import java.util.List;

public class User {
    private long id;
    private String emailAddress;
    private String password;
    private boolean isNotified;
    private Timestamp loginDate;
    private List<Article> articles;

    public User() {
    }

    public User(UserRequestDto request) {
        setEmailAddress(request.getEmailAddress());
        setPassword(request.getPassword());
        setNotified(request.isNotified());
        setLoginDate(request.getLoginDate());
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

    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", isNotified=" + isNotified +
                ", loginDate=" + loginDate +
                ", articles=" + articles +
                '}';
    }
}
