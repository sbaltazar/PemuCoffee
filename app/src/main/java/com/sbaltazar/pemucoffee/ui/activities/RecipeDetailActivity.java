package com.sbaltazar.pemucoffee.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.viewmodels.RecipeViewModel;
import com.sbaltazar.pemucoffee.databinding.ActivityRecipeDetailBinding;
import com.sbaltazar.pemucoffee.ui.fragments.RecipeListFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    private ActivityRecipeDetailBinding mBinding;

    private int mRecipeId = 0;
    private String mRecipeName = "";

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

                mRecipeName = recipe.getName();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_share_recipe) {
            shareRecipe();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareRecipe() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("I love my coffee recipe %s", mRecipeName));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }
}
