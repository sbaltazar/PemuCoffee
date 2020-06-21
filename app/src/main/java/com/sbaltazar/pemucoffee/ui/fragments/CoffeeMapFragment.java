package com.sbaltazar.pemucoffee.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.FragmentCoffeeMapBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoffeeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoffeeMapFragment extends Fragment {

    private FragmentCoffeeMapBinding mBinding;

    public CoffeeMapFragment() { }

    public static CoffeeMapFragment newInstance() {
        CoffeeMapFragment fragment = new CoffeeMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentCoffeeMapBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }
}
