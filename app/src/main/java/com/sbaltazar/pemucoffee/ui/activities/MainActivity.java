package com.sbaltazar.pemucoffee.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.raw.RecipeRaw;
import com.sbaltazar.pemucoffee.data.viewmodels.RecipeViewModel;
import com.sbaltazar.pemucoffee.databinding.ActivityMainBinding;
import com.sbaltazar.pemucoffee.service.PemuCoffeApi;
import com.sbaltazar.pemucoffee.ui.fragments.RecipeListFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = mBinding.getRoot();
        setContentView(view);

        mFragmentManager = getSupportFragmentManager();
        RecipeListFragment recipeListFragment = RecipeListFragment.newInstance();

        mFragmentManager.beginTransaction()
                .add(R.id.container, recipeListFragment)
                .commit();

        //new DownloadRecipesAsyncTask(this).execute();

        mBinding.bottomNavbarView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.recipe_page:
                    mFragmentManager.beginTransaction().replace(R.id.container, recipeListFragment);
                    break;
                case R.id.brewing_page:
                    mFragmentManager.beginTransaction().replace(R.id.container, recipeListFragment);
                    break;
                case R.id.find_shop_page:
                    mFragmentManager.beginTransaction().replace(R.id.container, recipeListFragment);
                    break;
            }

            return true;
        });

        downloadData();
    }

    private void downloadData() {
        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        recipeViewModel.getAllRecipes().observe(this, recipes -> {
            if (recipes == null || recipes.isEmpty()) {
                recipeViewModel.insertFromApi();
            } else {
                Fragment currentFragment = mFragmentManager.findFragmentById(R.id.container);

                if (currentFragment instanceof RecipeListFragment) {
                    ((RecipeListFragment) currentFragment).setRecipes(recipes);
                }

            }
        });
    }

    private static class DownloadRecipesAsyncTask extends AsyncTask<Void, Void, List<RecipeRaw>> {
        // Weakreference for avoiding context leak
        private WeakReference<Context> mContextReference;

        DownloadRecipesAsyncTask(Context context) {
            mContextReference = new WeakReference<>(context);
        }

        @Override
        protected List<RecipeRaw> doInBackground(Void... voids) {

            if (mContextReference.get() == null) return null;

            Call<List<RecipeRaw>> recipesCall = PemuCoffeApi.getService().getRecipes();

            try {
                Response<List<RecipeRaw>> recipesResponse = recipesCall.execute();

                if (recipesResponse.isSuccessful() && recipesResponse.body() != null) {
                    return recipesResponse.body();
                } else {
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<RecipeRaw> recipes) {

            String toastMessage = "";

            if (recipes == null) {
                toastMessage = "Hubo un problema al descargar las recetas";
            } else {
                toastMessage = "Despachos descargados correctamente";
            }

            if (mContextReference.get() != null) {
                Toast.makeText(mContextReference.get(), toastMessage,
                        Toast.LENGTH_SHORT).show();
            }

        }

    }
}
