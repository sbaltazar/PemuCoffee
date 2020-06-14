package com.sbaltazar.pemucoffee.ui.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ItemReorderBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReorderItemAdapter extends RecyclerView.Adapter<ReorderItemAdapter.ReordenItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<String> mItems;

    public ReorderItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList<>();
        mItems.add("");
    }

    @NonNull
    @Override
    public ReordenItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReorderBinding binding = ItemReorderBinding.inflate(mInflater, parent, false);
        return new ReordenItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReordenItemViewHolder holder, int position) {
        if (mItems != null) {
            String item = mItems.get(position);
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    public void setItems(List<String> items) {
        mItems = items;
        mItems.add("");
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    class ReordenItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemReorderBinding mBinding;

        ReordenItemViewHolder(@NonNull ItemReorderBinding binding) {
            super(binding.getRoot());
            // View binding for getting UI references
            mBinding = binding;
        }

        void bind(String item) {

            // If is the last item
            if (getAdapterPosition() == mItems.size() - 1) {
                mBinding.icReorder.setImageResource(R.drawable.ic_add_gray_800_24dp);
                mBinding.icClose.setVisibility(View.INVISIBLE);

                RecyclerView.ViewHolder viewHolder = this;

                mBinding.etReorderItem.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 1) {
                            mItems.add("");
                            notifyItemInserted(getAdapterPosition() + 1);

                            mBinding.icReorder.setImageResource(R.drawable.ic_reorder_gray_800_24dp);
                            mBinding.icClose.setVisibility(View.VISIBLE);

                            mBinding.icClose.setOnClickListener(view -> {
                                mBinding.etReorderItem.getText().clear();
                                mItems.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            });

                            mBinding.etReorderItem.removeTextChangedListener(this);
                        }
                    }
                });

            } else {
                mBinding.icReorder.setImageResource(R.drawable.ic_reorder_gray_800_24dp);
                mBinding.icClose.setVisibility(View.VISIBLE);
                mBinding.etReorderItem.setText(item);

                mBinding.icClose.setOnClickListener(v -> {
                    mItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                });
            }

        }
    }
}
