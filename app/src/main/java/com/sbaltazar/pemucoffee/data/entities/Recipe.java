package com.sbaltazar.pemucoffee.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey
    private int id;
    private String name;
    private String imageUrl;
    private List<String> ingredients;
    private List<String> methods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
}
