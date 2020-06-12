package com.sbaltazar.pemucoffee.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.databinding.FragmentRecipeListBinding;
import com.sbaltazar.pemucoffee.ui.adapters.RecipeItemAdapter;

import java.util.List;

public class RecipeListFragment extends Fragment implements RecipeItemAdapter.RecipeClickListener {

    private RecipeItemAdapter mRecipeAdapter;
    private FragmentRecipeListBinding mBinding;

    public RecipeListFragment() { }

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
        Toast.makeText(getContext(), recipe.getName(), Toast.LENGTH_SHORT).show();
    }

    public void setRecipes(List<Recipe> recipes){
        mRecipeAdapter.setRecipes(recipes);
    }
}
