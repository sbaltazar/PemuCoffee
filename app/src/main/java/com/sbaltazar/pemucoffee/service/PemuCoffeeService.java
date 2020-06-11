package com.sbaltazar.pemucoffee.service;

import com.sbaltazar.pemucoffee.data.raw.RecipeRaw;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PemuCoffeeService {

    String API_URL = "https://pemucoffee.azurewebsites.net";

    @GET("/api/GetRecipe")
    Call<List<RecipeRaw>> getRecipes();

}
