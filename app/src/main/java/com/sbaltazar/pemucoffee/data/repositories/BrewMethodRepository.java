package com.sbaltazar.pemucoffee.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sbaltazar.pemucoffee.data.PemuCoffeeDatabase;
import com.sbaltazar.pemucoffee.data.dao.BrewMethodDao;
import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.data.mappers.BrewMethodMapper;
import com.sbaltazar.pemucoffee.data.raw.BrewMethodRaw;
import com.sbaltazar.pemucoffee.service.PemuCoffeApi;
import com.sbaltazar.pemucoffee.service.PemuCoffeeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BrewMethodRepository {

    private BrewMethodDao mDao;
    private LiveData<List<BrewMethod>> mAllBrewMethods;
    private PemuCoffeeService mApi;

    public BrewMethodRepository(Application application) {
        PemuCoffeeDatabase database = PemuCoffeeDatabase.getDatabase(application);
        mDao = database.brewMethodDao();
        mAllBrewMethods = mDao.getAllBrewMethods();
        mApi = PemuCoffeApi.getService();
    }

    public LiveData<List<BrewMethod>> getAllBrewMethods() {
        return mAllBrewMethods;
    }

    public void insertFromApi() {
        new insertFromApiAsyncTask(mApi, mDao).execute();
    }

    private static class insertFromApiAsyncTask extends AsyncTask<Void, Void, Void> {

        final private PemuCoffeeService mService;
        final private BrewMethodDao mDao;

        insertFromApiAsyncTask(PemuCoffeeService api, BrewMethodDao dao) {
            mService = api;
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Call<List<BrewMethodRaw>> call = mService.getBrewMethods();

            try {
                Response<List<BrewMethodRaw>> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {

                    BrewMethodMapper mapper = new BrewMethodMapper();

                    List<BrewMethodRaw> brewMethodRawList = response.body();
                    List<BrewMethod> brewMethods = new ArrayList<>();

                    for (BrewMethodRaw brewMethodRaw: brewMethodRawList) {
                        brewMethods.add(mapper.apply(brewMethodRaw));
                    }

                    mDao.insert(brewMethods);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
