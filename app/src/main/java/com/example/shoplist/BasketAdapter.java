package com.example.shoplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoplist.Entities.Basket;

public class BasketAdapter extends ListAdapter<Basket, BasketAdapter.BasketHolder> {

    private setOnLongClickListener onLongClickListener;
    private setOnCheckBoxClickListener onCheckBoxClickListener;

    public BasketAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Basket> DIFF_CALLBACK = new DiffUtil.ItemCallback<Basket>() {
        @Override
        public boolean areItemsTheSame(@NonNull Basket oldItem, @NonNull Basket newItem) {
            return oldItem.getBasketId() == newItem.getBasketId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Basket oldItem, @NonNull Basket newItem) {
            return oldItem.getProductName().matches(newItem.getProductName()) &&
                    oldItem.getProductCount().matches(newItem.getProductCount())
                    && oldItem.getChecked() == newItem.getChecked();
        }
    };

    @NonNull
    @Override
    public BasketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item, parent, false);
        return new BasketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketHolder holder, int position) {
        Basket basket = getItem(position);
        holder.textViewProductName.setText(basket.getProductName());
        holder.textViewProductCount.setText(String.valueOf(basket.getProductCount()));
        holder.checkBoxChecked.setChecked(basket.getChecked());
    }

    public Basket getBasketAt(int position) {
        return getItem(position);
    }

    class BasketHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName;
        private EditText editTextProductName;
        private TextView textViewProductCount;
        private EditText editTextProductCount;
        private CheckBox checkBoxChecked;
        BasketHolder basketHolder1;

        public BasketHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.text_view_productName);
            editTextProductName = itemView.findViewById(R.id.edit_text_productName);
            textViewProductCount = itemView.findViewById(R.id.text_view_productCount);
            editTextProductCount = itemView.findViewById(R.id.edit_text_productCount);
            checkBoxChecked = itemView.findViewById(R.id.checkbox_checked);
            basketHolder1 = this;

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (onLongClickListener != null && position != RecyclerView.NO_POSITION) {
                        onLongClickListener.onItemLongClick(getItem(position));
                    }
                    if (!CreateBasket.isEditing) {
                        CreateBasket.isEditing = true;
                        setVisiblesToUpdateState();
                        basketHolder = basketHolder1;
                    }
                    return false;
                }
            });

            checkBoxChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onCheckBoxClickListener != null && position != RecyclerView.NO_POSITION) {
                        onCheckBoxClickListener.onCheckBoxClick(getItem(position));
                    }
                }
            });
        }

        public void setVisiblesToUpdateState() {
            textViewProductName.setVisibility(View.GONE);
            editTextProductName.setVisibility(View.VISIBLE);
            textViewProductCount.setVisibility(View.GONE);
            editTextProductCount.setVisibility(View.VISIBLE);
            editTextProductName.setText(textViewProductName.getText().toString());
            editTextProductCount.setText(textViewProductCount.getText().toString());
        }

        public void setVisiblesToShowState() {
            textViewProductName.setVisibility(View.VISIBLE);
            editTextProductName.setVisibility(View.GONE);
            textViewProductCount.setVisibility(View.VISIBLE);
            editTextProductCount.setVisibility(View.GONE);
        }

        public String getProductName() {
            return editTextProductName.getText().toString();
        }


        public String getProductCount() {
            return editTextProductCount.getText().toString();
        }

        public boolean isChecked() {
            return checkBoxChecked.isChecked();
        }
    }

    private BasketHolder basketHolder;

    public BasketHolder getBasketHolder() {
        return basketHolder;
    }

    public interface setOnLongClickListener {
        void onItemLongClick(Basket basket);
    }

    public void setOnLongClickListener(setOnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public interface setOnCheckBoxClickListener {
        void onCheckBoxClick(Basket basket);
    }

    public void setOnCheckBoxClickListener(setOnCheckBoxClickListener listener) {
        this.onCheckBoxClickListener = listener;
    }
}