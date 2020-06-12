package com.sbaltazar.pemucoffee.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sbaltazar.pemucoffee.data.entities.BrewMethod;

import java.util.List;

@Dao
public interface BrewMethodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BrewMethod brewMethod);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<BrewMethod> brewMethod);

    @Query("DELETE FROM brew_methods")
    void deleteAll();

    @Query("SELECT * FROM brew_methods WHERE id = :id")
    LiveData<List<BrewMethod>> getBrewMethod(int id);

    @Query("SELECT * FROM brew_methods ORDER BY id ASC")
    LiveData<List<BrewMethod>> getAllBrewMethods();
}
