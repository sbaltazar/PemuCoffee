package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ActivityAddRecipeBinding;
import com.sbaltazar.pemucoffee.ui.adapters.ReorderItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private ActivityAddRecipeBinding mBinding;
    private ReorderItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddRecipeBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_new_recipe);
        }

        mAdapter = new ReorderItemAdapter(this);

        List<String> ingredients = new ArrayList<>();

//        ingredients.add("Ingredient 1");
//        ingredients.add("Ingredient 2");
//        ingredients.add("Ingredient 3");
//        ingredients.add("Ingredient 4");

        mBinding.rvRecipeIngredients.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRecipeIngredients.setAdapter(mAdapter);

        mAdapter.setItems(ingredients);

    }

}
