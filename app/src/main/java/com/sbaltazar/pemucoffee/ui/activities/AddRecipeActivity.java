package com.sbaltazar.pemucoffee.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.data.viewmodels.RecipeViewModel;
import com.sbaltazar.pemucoffee.databinding.ActivityAddRecipeBinding;
import com.sbaltazar.pemucoffee.ui.adapters.ReorderItemAdapter;

import java.util.List;

import timber.log.Timber;


public class AddRecipeActivity extends AppCompatActivity implements ReorderItemAdapter.DragItemListener {

    private ActivityAddRecipeBinding mBinding;
    private ReorderItemAdapter mIngredientAdapter;
    private ReorderItemAdapter mMethodAdapter;

    private ItemTouchHelper mIngredientItemTouchHelper;
    private ItemTouchHelper mMethodItemTouchHelper;

    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddRecipeBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_new_recipe);
        }

        mIngredientAdapter = new ReorderItemAdapter(this, this, R.string.hint_add_ingredient);
        mMethodAdapter = new ReorderItemAdapter(this, this, R.string.hint_add_method);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        mBinding.rvRecipeIngredients.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRecipeIngredients.setAdapter(mIngredientAdapter);

        mBinding.rvRecipeMethods.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRecipeMethods.setAdapter(mMethodAdapter);

        initIngredientTouchHelper();
        initMethodTouchHelper();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_add_recipe) {

            saveRecipe();

            Timber.d(mIngredientAdapter.getItems().toString());
            Timber.d(mMethodAdapter.getItems().toString());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mIngredientItemTouchHelper.startDrag(viewHolder);
        mMethodItemTouchHelper.startDrag(viewHolder);
    }

    private void initIngredientTouchHelper() {

        mIngredientItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                final int fromPos = viewHolder.getAdapterPosition();
                final int toPost = target.getAdapterPosition();

                mIngredientAdapter.moveItem(fromPos, toPost);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });

        mIngredientItemTouchHelper.attachToRecyclerView(mBinding.rvRecipeIngredients);
    }

    private void initMethodTouchHelper() {

        mMethodItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                final int fromPos = viewHolder.getAdapterPosition();
                final int toPost = target.getAdapterPosition();

                mMethodAdapter.moveItem(fromPos, toPost);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });

        mMethodItemTouchHelper.attachToRecyclerView(mBinding.rvRecipeMethods);
    }

    private void saveRecipe() {

        String recipeName = mBinding.etRecipeName.getEditableText().toString();

        if (TextUtils.isEmpty(recipeName)) {
            mBinding.tilRecipeName.setErrorEnabled(true);
            mBinding.tilRecipeName.setError(getResources().getString(R.string.error_no_recipe_name));
            return;
        } else {
            mBinding.tilRecipeName.setError(null);
            mBinding.tilRecipeName.setErrorEnabled(false);
        }

        // Instantiating a LiveData object to avoid creating two references when inserting inside
        // the same observing event
        final LiveData<Integer> getLastIdObservable = mRecipeViewModel.getLastId();

        getLastIdObservable.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer lastId) {
                Recipe recipe = new Recipe();

                List<String> ingredients = mIngredientAdapter.getItems();
                // Removing last ingredient that is an empty string
                ingredients.remove(ingredients.size() - 1);

                List<String> methods = mMethodAdapter.getItems();
                // Removing last method that is an empty string
                methods.remove(methods.size() - 1);

                recipe.setName(recipeName);
                recipe.setIngredients(ingredients);
                recipe.setMethods(methods);

                recipe.setId(lastId + 1);
                // Removing observers before insert to avoid calling more times
                getLastIdObservable.removeObserver(this);

                mRecipeViewModel.insert(recipe);

                finish();
            }
        });


    }

}
