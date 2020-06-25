package com.sbaltazar.pemucoffee.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.databinding.FragmentBrewMethodListBinding;
import com.sbaltazar.pemucoffee.ui.activities.BrewMethodDetailActivity;
import com.sbaltazar.pemucoffee.ui.adapters.BrewMethodItemAdapter;

import java.util.List;

public class BrewMethodListFragment extends Fragment implements BrewMethodItemAdapter.BrewMethodClickListener {

    // Intent TAG to pass the brew method ID
    public static final String EXTRA_BREW_METHOD_ID = "brew_method_id";

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

        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), BrewMethodDetailActivity.class);
            intent.putExtra(EXTRA_BREW_METHOD_ID, brewMethod.getId());

            getActivity().startActivity(intent);
        }
    }

    public void setBrewMethods(List<BrewMethod> brewMethods) {
        mBrewMethodAdapter.setBrewMethods(brewMethods);
    }
}
