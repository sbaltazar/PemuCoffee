package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.viewmodels.RecipeViewModel;
import com.sbaltazar.pemucoffee.databinding.ActivityRecipeDetailBinding;
import com.sbaltazar.pemucoffee.ui.fragments.RecipeListFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    private ActivityRecipeDetailBinding mBinding;

    private int mRecipeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        setSupportActionBar(mBinding.toolbarRecipe);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().hasExtra(RecipeListFragment.EXTRA_RECIPE_ID)) {
            mRecipeId = getIntent().getIntExtra(RecipeListFragment.EXTRA_RECIPE_ID, -1);
        }

        if (mRecipeId <= 0) {
            finish();
        }

        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        recipeViewModel.getRecipe(mRecipeId).observe(this, recipe -> {
            if (recipe != null) {
                mBinding.collapsingToolbarRecipe.setTitle(recipe.getName());

                StringBuilder ingredientList = new StringBuilder();
                StringBuilder methodList = new StringBuilder();

                for (String ingredient : recipe.getIngredients()) {
                    ingredientList.append("\u2022\u0020").append(ingredient).append("\n");
                }

                for (int i = 0; i < recipe.getMethods().size(); i++) {
                    methodList.append(i + 1).append("-.\u0020").append(recipe.getMethods().get(i)).append("\n\n");
                }

                Glide.with(this).load(recipe.getImageUrl()).into(mBinding.toolbarImageRecipe);

                mBinding.tvRecipeIngredientList.setText(ingredientList);
                mBinding.tvRecipeMethodList.setText(methodList);
            }
        });
    }
}
