package com.sbaltazar.pemucoffee.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbaltazar.pemucoffee.R;
import com.sbaltazar.pemucoffee.databinding.ItemReorderBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReorderItemAdapter extends RecyclerView.Adapter<ReorderItemAdapter.ReordenItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<String> mItems;

    final private DragItemListener mDragItemListener;

    public interface DragItemListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public ReorderItemAdapter(Context context, DragItemListener listener) {
        mInflater = LayoutInflater.from(context);
        mDragItemListener = listener;
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

    public List<String> getItems(){
        return mItems;
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

    public void moveItem(int fromPos, int toPos) {

        if (toPos < mItems.size() - 1) {
            if (fromPos < toPos) {
                for (int i = fromPos; i < toPos; i++) {
                    Collections.swap(mItems, i, i + 1);
                }
            } else {
                for (int i = fromPos; i > toPos; i--) {
                    Collections.swap(mItems, i, i - 1);
                }
            }

            notifyItemMoved(fromPos, toPos);
        }
    }

    class ReordenItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemReorderBinding mBinding;

        ReordenItemViewHolder(@NonNull ItemReorderBinding binding) {
            super(binding.getRoot());
            // View binding for getting UI references
            mBinding = binding;
        }

        @SuppressLint("ClickableViewAccessibility")
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

                mBinding.etReorderItem.setOnFocusChangeListener((v, hasFocus) -> {
                    if (!hasFocus) {
                        String typedText = mBinding.etReorderItem.getText().toString();

                        mItems.set(getAdapterPosition(), typedText);
                    }
                });

                mBinding.icReorder.setOnTouchListener((v, event) -> {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                        mDragItemListener.onStartDrag(viewHolder);
                    }
                    return false;
                });

            } else {
//                mBinding.icReorder.setImageResource(R.drawable.ic_reorder_gray_800_24dp);
//                mBinding.icClose.setVisibility(View.VISIBLE);
//                mBinding.etReorderItem.setText(item);
//
//                mBinding.icClose.setOnClickListener(v -> {
//                    mItems.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                });
            }

        }
    }
}
