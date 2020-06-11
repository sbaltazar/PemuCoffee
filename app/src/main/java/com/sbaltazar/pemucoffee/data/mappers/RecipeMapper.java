package com.sbaltazar.pemucoffee.data.mappers;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import com.sbaltazar.pemucoffee.data.MissingParamsException;
import com.sbaltazar.pemucoffee.data.entities.Recipe;
import com.sbaltazar.pemucoffee.data.raw.RecipeRaw;


public class RecipeMapper implements Function<RecipeRaw, Recipe> {

    @Override
    public Recipe apply(RecipeRaw raw) {

        assertParams(raw);

        Recipe recipe = new Recipe();

        recipe.setId(raw.getId());
        recipe.setName(raw.getName());
        recipe.setImageUrl(raw.getImageUrl());
        recipe.setIngredients(raw.getIngredients());
        recipe.setMethods(raw.getMethods());

        return recipe;
    }

    private static void assertParams(@NonNull final RecipeRaw raw) {
        String missingParams = "";

        if (raw.getId() <= 0) {
            missingParams += "id";
        }

        if (raw.getName() == null || raw.getName().isEmpty()) {
            missingParams += " name";
        }

        if (raw.getImageUrl() == null || raw.getImageUrl().isEmpty()) {
            missingParams += " imageUrl";
        }

        if (raw.getIngredients() == null) {
            missingParams += " ingredients";
        }

        if (raw.getMethods() == null) {
            missingParams += " methods";
        }

        if (!missingParams.isEmpty()) {
            throw new MissingParamsException(missingParams, raw);
        }
    }

}
