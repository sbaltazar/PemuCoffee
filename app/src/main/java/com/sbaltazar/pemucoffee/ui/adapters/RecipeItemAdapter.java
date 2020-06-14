package com.sbaltazar.pemucoffee.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.databinding.CardRecipeBinding;

import java.util.List;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.RecipeItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes;

    final private RecipeClickListener mClickListener;

    public interface RecipeClickListener {
        void onRecipeClick(View view, int position);
    }

    public RecipeItemAdapter(Context context, RecipeClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mClickListener = listener;
    }

    @NonNull
    @Override
    public RecipeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardRecipeBinding binding = CardRecipeBinding.inflate(mInflater, parent, false);
        return new RecipeItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItemViewHolder holder, int position) {
        if (mRecipes != null) {
            Recipe recipe = mRecipes.get(position);
            holder.bind(recipe);
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        }
        return 0;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getRecipe(int position) {
        if (mRecipes != null) {
            return mRecipes.get(position);
        }
        return null;
    }

    class RecipeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardRecipeBinding mBinding;

        RecipeItemViewHolder(@NonNull CardRecipeBinding binding) {
            super(binding.getRoot());
            // View binding for getting UI references
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        void bind(Recipe recipe) {
            if (recipe != null) {
                mBinding.itemRecipeTitle.setText(recipe.getName());
                Glide.with(mBinding.itemRecipeImage.getContext())
                        .load(recipe.getImageUrl()).into(mBinding.itemRecipeImage);
            }
        }

        @Override
        public void onClick(View v) {
            mClickListener.onRecipeClick(v, getAdapterPosition());
        }
    }

}
