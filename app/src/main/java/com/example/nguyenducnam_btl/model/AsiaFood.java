package com.example.nguyenducnam_btl.model;

public class AsiaFood {
    String key;
    String name;
    String price;
    Integer imageUrl;
    String rating;
    String description;

    public AsiaFood(String key, String name, String price, Integer imageUrl, String rating, String description) {
        this.key = key;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.description = description;
    }

    @Override
    public String toString() {
        return "AsiaFood{" +
                "id='" + key + '\'' +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imageUrl=" + imageUrl +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
