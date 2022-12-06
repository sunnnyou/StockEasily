package de.throsenheim.unlimited.stockeasilyapi.model;

public class UserArticle {

    private long userId;

    private long articleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "UserArticle{" +
                "userId=" + userId +
                ", articleId=" + articleId +
                '}';
    }
}
