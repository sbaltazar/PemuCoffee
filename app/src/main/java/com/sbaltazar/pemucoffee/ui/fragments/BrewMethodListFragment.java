package com.sbaltazar.pemucoffee.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.databinding.FragmentBrewMethodListBinding;
import com.sbaltazar.pemucoffee.ui.adapters.BrewMethodItemAdapter;

import java.util.List;

public class BrewMethodListFragment extends Fragment implements BrewMethodItemAdapter.BrewMethodClickListener {

    private BrewMethodItemAdapter mBrewMethodAdapter;
    private FragmentBrewMethodListBinding mBinding;

    public BrewMethodListFragment() {
    }

    public static BrewMethodListFragment newInstance() {
        BrewMethodListFragment fragment = new BrewMethodListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mBrewMethodAdapter = new BrewMethodItemAdapter(context, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentBrewMethodListBinding.inflate(inflater, container, false);

        mBinding.rvBrewMethods.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBinding.rvBrewMethods.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvBrewMethods.setAdapter(mBrewMethodAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onBrewMethodClick(View view, int position) {
        BrewMethod brewMethod = mBrewMethodAdapter.getBrewMethod(position);
        Toast.makeText(getContext(), brewMethod.getName(), Toast.LENGTH_SHORT).show();
    }

    public void setBrewMethods(List<BrewMethod> brewMethods) {
        mBrewMethodAdapter.setBrewMethods(brewMethods);
    }
}
