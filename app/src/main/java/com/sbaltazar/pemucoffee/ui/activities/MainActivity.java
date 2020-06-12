package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.raw.RecipeRaw;
import com.sbaltazar.pemucoffee.data.viewmodels.BrewMethodViewModel;
import com.sbaltazar.pemucoffee.data.viewmodels.RecipeViewModel;
import com.sbaltazar.pemucoffee.databinding.ActivityMainBinding;
import com.sbaltazar.pemucoffee.service.PemuCoffeApi;
import com.sbaltazar.pemucoffee.ui.fragments.BrewMethodListFragment;
import com.sbaltazar.pemucoffee.ui.fragments.RecipeListFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    private FragmentManager mFragmentManager;

    // ViewModels
    private RecipeViewModel mRecipeViewModel;
    private BrewMethodViewModel mBrewMethodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        mFragmentManager = getSupportFragmentManager();

        RecipeListFragment recipeListFragment = RecipeListFragment.newInstance();
        BrewMethodListFragment brewMethodListFragment = BrewMethodListFragment.newInstance();

        mFragmentManager.beginTransaction()
                .add(R.id.container, recipeListFragment)
                .commit();

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mBrewMethodViewModel = new ViewModelProvider(this).get(BrewMethodViewModel.class);

        mRecipeViewModel.getAllRecipes().observe(this, recipes -> {
            if (recipes != null) {
                recipeListFragment.setRecipes(recipes);
            }
        });

        mBinding.bottomNavbarView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.recipe_page:
                    mFragmentManager.beginTransaction().replace(R.id.container, recipeListFragment).commitNow();

                    mRecipeViewModel.getAllRecipes().observe(this, recipes -> {
                        if (recipes != null) {
                            recipeListFragment.setRecipes(recipes);
                        }
                    });
                    break;
                case R.id.brewing_page:
                    mFragmentManager.beginTransaction().replace(R.id.container, brewMethodListFragment).commitNow();

                    mBrewMethodViewModel.getAllBrewMethods().observe(this, brewMethods -> {
                        if (brewMethods != null) {
                            brewMethodListFragment.setBrewMethods(brewMethods);
                        }
                    });
                    break;
                case R.id.find_shop_page:
                    mFragmentManager.beginTransaction().replace(R.id.container, brewMethodListFragment).commitNow();
                    break;
            }

            return true;
        });

        downloadData();
    }

    private void downloadData() {

        mRecipeViewModel.getAllRecipes().observe(this, recipes -> {
            if (recipes == null || recipes.isEmpty()) {
                mRecipeViewModel.insertFromApi();
            }
        });

        mBrewMethodViewModel.getAllBrewMethods().observe(this, brewMethods -> {
            if (brewMethods == null || brewMethods.isEmpty()) {
                mBrewMethodViewModel.insertFromApi();
            }
        });


    }

}
