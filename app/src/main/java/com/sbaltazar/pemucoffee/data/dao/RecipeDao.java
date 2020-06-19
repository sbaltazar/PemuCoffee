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

    @Query("SELECT IFNULL(id, 0) FROM recipes ORDER BY id DESC LIMIT 1")
    LiveData<Integer> getLastId();

    @Query("SELECT * FROM recipes WHERE id = :id")
    LiveData<Recipe> getRecipe(int id);

    @Query("SELECT * FROM recipes ORDER BY id ASC")
    LiveData<List<Recipe>> getAllRecipes();

}
