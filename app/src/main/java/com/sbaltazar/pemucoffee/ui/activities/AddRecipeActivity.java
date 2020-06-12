package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ActivityAddRecipeBinding;

public class AddRecipeActivity extends AppCompatActivity {

    private ActivityAddRecipeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddRecipeBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_new_recipe);
        }
    }
}
