package com.sbaltazar.pemucoffee.data;

import androidx.annotation.NonNull;

public class MissingParamsException extends RuntimeException {

    public MissingParamsException(@NonNull final String missingParam, @NonNull final Object rawObject) {
        super("The params: " + missingParam + " are missing on the received" + rawObject);
    }
}

