package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ActivityMainBinding;
import com.sbaltazar.pemucoffee.ui.fragments.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeListFragment recipeListFragment = RecipeListFragment.newInstance();

        fragmentManager.beginTransaction()
                .add(R.id.container, recipeListFragment)
                .commit();

        mBinding.bottomNavbarView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.recipe_page:
                    fragmentManager.beginTransaction().replace(R.id.container, recipeListFragment);
                    break;
                case R.id.brewing_page:
                    fragmentManager.beginTransaction().replace(R.id.container, recipeListFragment);
                    break;
                case R.id.find_shop_page:
                    fragmentManager.beginTransaction().replace(R.id.container, recipeListFragment);
                    break;
            }

            return true;
        });

    }
}
