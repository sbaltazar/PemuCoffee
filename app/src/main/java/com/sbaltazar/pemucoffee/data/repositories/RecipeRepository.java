package com.sbaltazar.pemucoffee.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sbaltazar.pemucoffee.data.PemuCoffeeDatabase;
import com.sbaltazar.pemucoffee.data.dao.RecipeDao;
import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.data.mappers.RecipeMapper;
import com.sbaltazar.pemucoffee.data.raw.RecipeRaw;
import com.sbaltazar.pemucoffee.service.PemuCoffeApi;
import com.sbaltazar.pemucoffee.service.PemuCoffeeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeRepository {

    private RecipeDao mDao;
    private LiveData<List<Recipe>> mAllRecipes;
    private PemuCoffeeService mApi;

    public RecipeRepository(Application application) {
        PemuCoffeeDatabase database = PemuCoffeeDatabase.getDatabase(application);
        mDao = database.recipeDao();
        mAllRecipes = mDao.getAllRecipes();
        mApi = PemuCoffeApi.getService();
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        return mAllRecipes;
    }

    public LiveData<Recipe> getRecipe(int id) {
        return mDao.getRecipe(id);
    }

    public void insert(Recipe recipe) {
        new insertAsyncTask(mDao).execute(recipe);
    }

    public void insertFromApi() {
        new insertFromApiAsyncTask(mApi, mDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private RecipeDao  mAsyncTaskDao;

        insertAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... recipeEntities) {
            mAsyncTaskDao.insert(recipeEntities[0]);
            return null;
        }
    }

    private static class insertFromApiAsyncTask extends AsyncTask<Void, Void, Void> {

        final private PemuCoffeeService mService;
        final private RecipeDao mDao;

        insertFromApiAsyncTask(PemuCoffeeService api, RecipeDao dao) {
            mService = api;
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Call<List<RecipeRaw>> call = mService.getRecipes();

            try {
                Response<List<RecipeRaw>> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {

                    RecipeMapper mapper = new RecipeMapper();

                    List<RecipeRaw> recipeRawList = response.body();
                    List<Recipe> recipes = new ArrayList<>();

                    for (RecipeRaw recipeRaw: recipeRawList) {
                        recipes.add(mapper.apply(recipeRaw));
                    }

                    mDao.insert(recipes);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
