package com.sbaltazar.pemucoffee.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PemuCoffeApi {

    public static PemuCoffeeService getService() {
        Retrofit serviceBuilder = new Retrofit.Builder()
                .baseUrl(PemuCoffeeService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return serviceBuilder.create(PemuCoffeeService.class);
    }
}
