package com.sbaltazar.pemucoffee.data.raw;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrewMethodRaw {

    @SerializedName("Id")
    private int id;

    @SerializedName("Name")
    private String name;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("Methods")
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

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
}
