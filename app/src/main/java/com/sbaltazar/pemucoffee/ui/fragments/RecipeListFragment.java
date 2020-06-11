package com.sbaltazar.pemucoffee.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.Recipe;
import com.sbaltazar.pemucoffee.databinding.FragmentRecipeListBinding;
import com.sbaltazar.pemucoffee.ui.adapters.RecipeItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment implements RecipeItemAdapter.RecipeClickListener {

    private RecipeItemAdapter mRecipeAdapter;
    private FragmentRecipeListBinding mBinding;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @return A new instance of fragment RecipeListFragment.
     */
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentRecipeListBinding.inflate(inflater, container, false);

        List<Recipe> recipes = new ArrayList<>();

        Recipe cappuccino = new Recipe();
        cappuccino.setName("Cappuccino");

        recipes.add(cappuccino);

        mRecipeAdapter.setRecipes(recipes);

        mBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
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
}
