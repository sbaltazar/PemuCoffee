package com.sbaltazar.pemucoffee.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ActivityAddRecipeBinding;
import com.sbaltazar.pemucoffee.ui.adapters.ReorderItemAdapter;

import timber.log.Timber;


public class AddRecipeActivity extends AppCompatActivity implements ReorderItemAdapter.DragItemListener {

    private ActivityAddRecipeBinding mBinding;
    private ReorderItemAdapter mIngredientAdapter;
    private ReorderItemAdapter mMethodAdapter;

    private ItemTouchHelper mIngredientItemTouchHelper;
    private ItemTouchHelper mMethodItemTouchHelper;

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

            // TODO: Save the ingredients and methods list to the database;

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
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }
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
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }
        });

        mMethodItemTouchHelper.attachToRecyclerView(mBinding.rvRecipeMethods);
    }

}
