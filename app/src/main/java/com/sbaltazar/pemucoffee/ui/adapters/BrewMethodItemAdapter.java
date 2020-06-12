package com.sbaltazar.pemucoffee.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sbaltazar.pemucoffee.data.entities.BrewMethod;
import com.sbaltazar.pemucoffee.databinding.CardBrewMethodBinding;

import java.util.List;

public class BrewMethodItemAdapter extends RecyclerView.Adapter<BrewMethodItemAdapter.BrewMethodItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<BrewMethod> mBrewMethods;

    final private BrewMethodClickListener mClickListener;

    public interface BrewMethodClickListener {
        void onBrewMethodClick(View view, int position);
    }

    public BrewMethodItemAdapter(Context context, BrewMethodClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mClickListener = listener;
    }

    @NonNull
    @Override
    public BrewMethodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardBrewMethodBinding binding = CardBrewMethodBinding.inflate(mInflater, parent, false);
        return new BrewMethodItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BrewMethodItemViewHolder holder, int position) {
        if (mBrewMethods != null) {
            BrewMethod brewMethod = mBrewMethods.get(position);
            holder.bind(brewMethod);
        }
    }

    @Override
    public int getItemCount() {

        if (mBrewMethods != null) {
            return mBrewMethods.size();
        }
        return 0;
    }

    public List<BrewMethod> getBrewMethods() {
        return mBrewMethods;
    }

    public void setBrewMethods(List<BrewMethod> brewMethods) {
        mBrewMethods = brewMethods;
        notifyDataSetChanged();
    }

    public BrewMethod getBrewMethod(int position) {
        if (mBrewMethods != null) {
            return mBrewMethods.get(position);
        }
        return null;
    }

    class BrewMethodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardBrewMethodBinding mBinding;

        public BrewMethodItemViewHolder(@NonNull CardBrewMethodBinding binding) {
            super(binding.getRoot());
            // View binding for getting UI references
            mBinding = binding;
        }

        void bind(BrewMethod brewMethod) {
            if (brewMethod != null) {
                mBinding.itemBrewMethodTitle.setText(brewMethod.getName());
                Glide.with(mBinding.itemBrewMethodImage.getContext())
                        .load(brewMethod.getImageUrl()).into(mBinding.itemBrewMethodImage);
            }
        }

        @Override
        public void onClick(View v) {
            mClickListener.onBrewMethodClick(v, getAdapterPosition());
        }
    }
}
