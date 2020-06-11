package com.sbaltazar.pemucoffee.data;

import java.util.List;

public class Recipe {

    private String name;
    private String imageUrl;
    private List<String> Methods;

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

    public List<String> getMethods() {
        return Methods;
    }

    public void setMethods(List<String> methods) {
        Methods = methods;
    }
}
