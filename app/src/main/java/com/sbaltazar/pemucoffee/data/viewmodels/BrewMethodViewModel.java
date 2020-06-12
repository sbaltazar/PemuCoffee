package com.sbaltazar.pemucoffee.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.data.repositories.BrewMethodRepository;

import java.util.List;

public class BrewMethodViewModel extends AndroidViewModel {

    private BrewMethodRepository mRepository;

    private LiveData<List<BrewMethod>> mAllBrewMethods;

    public BrewMethodViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BrewMethodRepository(application);
        mAllBrewMethods = mRepository.getAllBrewMethods();
    }

    public LiveData<List<BrewMethod>> getAllBrewMethods() {
        return mAllBrewMethods;
    }

    public LiveData<BrewMethod> getBrewMethod(int id) {
        return mRepository.getBrewMethod(id);
    }

    public void insertFromApi() {
        mRepository.insertFromApi();
    }
}
