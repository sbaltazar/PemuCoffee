package com.sbaltazar.pemucoffee.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.viewmodels.RecipeViewModel;
import com.sbaltazar.pemucoffee.databinding.WidgetConfigurationPemucoffeeBinding;
import com.sbaltazar.pemucoffee.ui.adapters.RecipeItemAdapter;

public class PemuCoffeeWidgetConfiguration extends AppCompatActivity implements RecipeItemAdapter.RecipeClickListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    WidgetConfigurationPemucoffeeBinding mBinding;

    private RecipeViewModel mRecipeViewModel;
    private RecipeItemAdapter mRecipeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        mBinding = WidgetConfigurationPemucoffeeBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.recipes);
        }

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mRecipeAdapter = new RecipeItemAdapter(this, this);
        mBinding.rvCoffeeRecipes.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.rvCoffeeRecipes.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvCoffeeRecipes.setAdapter(mRecipeAdapter);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        mRecipeViewModel.getAllRecipes().observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                mRecipeAdapter.setRecipes(recipes);
            }
        });


    }

    @Override
    public void onRecipeClick(View view, int position) {
        // Push widget update to surface with newly set prefix
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        PemuCoffeeWidget.updateAppWidget(this, appWidgetManager,
                mAppWidgetId, mRecipeAdapter.getRecipe(position));

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
