package com.sbaltazar.pemucoffee.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.databinding.FragmentRecipeListBinding;
import com.sbaltazar.pemucoffee.ui.activities.AddRecipeActivity;
import com.sbaltazar.pemucoffee.ui.activities.RecipeDetailActivity;
import com.sbaltazar.pemucoffee.ui.adapters.RecipeItemAdapter;

import java.util.List;

public class RecipeListFragment extends Fragment implements RecipeItemAdapter.RecipeClickListener {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    private RecipeItemAdapter mRecipeAdapter;
    private FragmentRecipeListBinding mBinding;

    public RecipeListFragment() {
    }

    public static RecipeListFragment newInstance() {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mRecipeAdapter = new RecipeItemAdapter(context, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentRecipeListBinding.inflate(inflater, container, false);

        mBinding.rvRecipes.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBinding.rvRecipes.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvRecipes.setAdapter(mRecipeAdapter);

        mBinding.fabAddRecipe.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onRecipeClick(View view, int position) {
        Recipe recipe = mRecipeAdapter.getRecipe(position);

        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
            intent.putExtra(EXTRA_RECIPE_ID, recipe.getId());

            getActivity().startActivity(intent);
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipeAdapter.setRecipes(recipes);
    }
}
