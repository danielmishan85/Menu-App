package com.example.myapplication.model;

public class Dish {

    String name, image, description, category;
    int price;

    public Dish() {}

    public Dish(String name, String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Dish(String name, String image, String description, int price, String category) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
