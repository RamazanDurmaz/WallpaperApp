package com.ramazandurmaz.walpaper.model.models;

public class Walpaper {
    private String imageUrl;
    private String categoryId;

    public Walpaper() {
    }
    public Walpaper(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Walpaper(String imageUrl, String categoryId) {
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageLink) {
        this.imageUrl = imageLink;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
