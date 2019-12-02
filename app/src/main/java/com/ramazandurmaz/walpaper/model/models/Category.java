package com.ramazandurmaz.walpaper.model.models;

public class Category {
    private String name;
    private String imageLink;

    public Category() {
    }

    public Category(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
