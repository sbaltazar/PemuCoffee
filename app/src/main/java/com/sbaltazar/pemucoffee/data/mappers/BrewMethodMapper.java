package com.sbaltazar.pemucoffee.data.mappers;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import com.sbaltazar.pemucoffee.data.MissingParamsException;
import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.data.raw.BrewMethodRaw;

public class BrewMethodMapper implements Function<BrewMethodRaw, BrewMethod> {

    @Override
    public BrewMethod apply(BrewMethodRaw raw) {

        assertParams(raw);

        BrewMethod brewMethod = new BrewMethod();

        brewMethod.setId(raw.getId());
        brewMethod.setName(raw.getName());
        brewMethod.setImageUrl(raw.getImageUrl());
        brewMethod.setMethods(raw.getMethods());

        return brewMethod;
    }

    private static void assertParams(@NonNull final BrewMethodRaw raw) {
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

        if (raw.getMethods() == null) {
            missingParams += " methods";
        }

        if (!missingParams.isEmpty()) {
            throw new MissingParamsException(missingParams, raw);
        }
    }
}
