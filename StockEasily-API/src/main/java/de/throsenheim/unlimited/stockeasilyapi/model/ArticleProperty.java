package de.throsenheim.unlimited.stockeasilyapi.model;

public class ArticleProperty {

    private long articleId;

    private long propertyId;


    public long getArticleId() {
        return articleId;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleProperty that = (ArticleProperty) o;
        return articleId == that.articleId && propertyId == that.propertyId;
    }

    @Override
    public String toString() {
        return "ArticleProperty{" +
                "articleId=" + articleId +
                ", propertyId=" + propertyId +
                '}';
    }
}
