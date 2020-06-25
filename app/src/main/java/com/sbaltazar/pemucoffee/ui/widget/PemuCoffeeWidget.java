package com.sbaltazar.pemucoffee.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.data.entities.Recipe;

public class PemuCoffeeWidget extends AppWidgetProvider {

    private static Recipe mRecipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
                                Recipe recipe) {

        mRecipe = recipe;

        // RemoteView object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_pemucoffee);

        if (recipe != null) {
            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

            if (TextUtils.isEmpty(recipe.getImageUrl())){
                views.setImageViewResource(R.id.widget_image_recipe, R.drawable.coffee_default);
            } else {
                AppWidgetTarget awt = new AppWidgetTarget(context, R.id.widget_image_recipe, views, appWidgetId) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                    }
                };

                RequestOptions options = new RequestOptions().
                        override(300, 300).placeholder(R.drawable.coffee_default);

                Glide.with(context.getApplicationContext())
                        .asBitmap()
                        .load(recipe.getImageUrl())
                        .apply(options)
                        .into(awt);
            }

            StringBuilder ingredientString = new StringBuilder();

            for (String ingredient : recipe.getIngredients()) {
                String ingredientItem = String.format("• %s\n", ingredient);
                ingredientString.append(ingredientItem);
            }

            views.setTextViewText(R.id.widget_recipe_ingredient_list, ingredientString.toString());

            StringBuilder methodString = new StringBuilder();

            for (String method : recipe.getMethods()) {
                String methodItem = String.format("• %s\n", method);
                methodString.append(methodItem);
            }

            views.setTextViewText(R.id.widget_recipe_method_list, methodString.toString());
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, mRecipe);
        }
    }
}
