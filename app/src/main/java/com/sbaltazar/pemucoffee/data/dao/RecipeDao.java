package com.sbaltazar.pemucoffee.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sbaltazar.pemucoffee.data.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Recipe> recipes);

    @Query("DELETE FROM recipes")
    void deleteAll();

    @Query("SELECT * FROM recipes WHERE id = :id")
    LiveData<Recipe> getRecipe(int id);

    @Query("SELECT * FROM recipes ORDER BY id ASC")
    LiveData<List<Recipe>> getAllRecipes();

}
