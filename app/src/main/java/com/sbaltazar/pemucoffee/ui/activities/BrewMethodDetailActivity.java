package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sbaltazar.pemucoffee.data.viewmodels.BrewMethodViewModel;
import com.sbaltazar.pemucoffee.databinding.ActivityBrewMethodDetailBinding;
import com.sbaltazar.pemucoffee.ui.fragments.BrewMethodListFragment;

public class BrewMethodDetailActivity extends AppCompatActivity {

    private ActivityBrewMethodDetailBinding mBinding;

    private int mBrewMethodId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBrewMethodDetailBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        setSupportActionBar(mBinding.toolbarBrewMethod);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().hasExtra(BrewMethodListFragment.EXTRA_BREW_METHOD_ID)) {
            mBrewMethodId = getIntent().getIntExtra(BrewMethodListFragment.EXTRA_BREW_METHOD_ID, -1);
        }

        if (mBrewMethodId <= 0) {
            finish();
        }

        BrewMethodViewModel brewMethodViewModel = new ViewModelProvider(this).get(BrewMethodViewModel.class);

        brewMethodViewModel.getBrewMethod(mBrewMethodId).observe(this, brewMethod -> {
            if (brewMethod != null) {
                mBinding.collapsingToolbarBrewMethod.setTitle(brewMethod.getName());

                StringBuilder methodList = new StringBuilder();
                for (int i = 0; i < brewMethod.getMethods().size(); i++) {
                    methodList.append(i + 1).append("-.\u0020").append(brewMethod.getMethods().get(i)).append("\n\n");
                }

                Glide.with(this).load(brewMethod.getImageUrl()).into(mBinding.toolbarImageBrewMethod);

                mBinding.tvBrewMethodMethodList.setText(methodList);

            }
        });


    }
}
