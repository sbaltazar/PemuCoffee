package com.sbaltazar.pemucoffee.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.data.repositories.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;

    private LiveData<List<Recipe>> mAllRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
    }

    public LiveData<Integer> getLastId() {
        return mRepository.getLastId();
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        return mAllRecipes;
    }

    public LiveData<Recipe> getRecipe(int id){
        return mRepository.getRecipe(id);
    }

    public void insert(Recipe recipe) {
        mRepository.insert(recipe);
    }

    public void insertFromApi(){
        mRepository.insertFromApi();
    }
}
