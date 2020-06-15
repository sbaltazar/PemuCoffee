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
import android.widget.Toast;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ActivityAddRecipeBinding;
import com.sbaltazar.pemucoffee.ui.adapters.ReorderItemAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class AddRecipeActivity extends AppCompatActivity implements ReorderItemAdapter.DragItemListener {

    private ActivityAddRecipeBinding mBinding;
    private ReorderItemAdapter mAdapter;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddRecipeBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_new_recipe);
        }

        mAdapter = new ReorderItemAdapter(this, this);

        List<String> ingredients = new ArrayList<>();

//        ingredients.add("Ingredient 1");
//        ingredients.add("Ingredient 2");
//        ingredients.add("Ingredient 3");
//        ingredients.add("Ingredient 4");

        mBinding.rvRecipeIngredients.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRecipeIngredients.setAdapter(mAdapter);

        mAdapter.setItems(ingredients);

        initTouchHelper();

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

            Timber.d(mAdapter.getItems().toString());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void initTouchHelper() {

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                final int fromPos = viewHolder.getAdapterPosition();
                final int toPost = target.getAdapterPosition();

                mAdapter.moveItem(fromPos, toPost);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        mItemTouchHelper.attachToRecyclerView(mBinding.rvRecipeIngredients);

    }

}
