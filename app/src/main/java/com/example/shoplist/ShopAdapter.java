package com.example.shoplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoplist.Entities.Shop;

public class ShopAdapter extends ListAdapter<Shop, ShopAdapter.ShopHolder> {

    private setOnLongClickListener onLongClickListener;
    private setOnClickListener onClickListener;

    public ShopAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Shop> DIFF_CALLBACK = new DiffUtil.ItemCallback<Shop>() {
        @Override
        public boolean areItemsTheSame(@NonNull Shop oldItem, @NonNull Shop newItem) {
            return oldItem.getShopId() == newItem.getShopId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Shop oldItem, @NonNull Shop newItem) {
            return oldItem.getShopName().matches(newItem.getShopName());
        }
    };

    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        return new ShopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder holder, int position) {
        Shop shop = getItem(position);
        holder.textViewShopName.setText(shop.getShopName());
    }

    public Shop getShopAt(int position) {
        return getItem(position);
    }

    class ShopHolder extends RecyclerView.ViewHolder {
        private TextView textViewShopName;
        private EditText editTextViewShopName;
        private ShopHolder shopHolder1;

        public ShopHolder(@NonNull View itemView) {
            super(itemView);
            textViewShopName = itemView.findViewById(R.id.text_view_shopName);
            editTextViewShopName = itemView.findViewById(R.id.edit_text_view_shopName);
            shopHolder1 = this;

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (onLongClickListener != null && position != RecyclerView.NO_POSITION) {
                        onLongClickListener.onItemLongClick(getItem(position));
                    }
                    if (!ShopList.isEditing) {
                        ShopList.isEditing = true;
                        setVisiblesToUpdateState();
                        shopHolder = shopHolder1;
                    }
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onClickListener != null && position != RecyclerView.NO_POSITION) {
                        onClickListener.onItemClick(getItem(position));
                    }
                }
            });
        }

        public void setVisiblesToUpdateState() {
            textViewShopName.setVisibility(View.GONE);
            editTextViewShopName.setVisibility(View.VISIBLE);
            editTextViewShopName.setText(textViewShopName.getText().toString());
        }

        public void setVisiblesToShowState() {
            textViewShopName.setVisibility(View.VISIBLE);
            editTextViewShopName.setVisibility(View.GONE);
        }

        public String getShopName() {
            return editTextViewShopName.getText().toString();
        }
    }


    private ShopHolder shopHolder;

    public ShopHolder getShopHolder() {
        return shopHolder;
    }

    public interface setOnLongClickListener {
        void onItemLongClick(Shop shop);
    }

    public void setOnLongClickListener(setOnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public interface setOnClickListener {
        void onItemClick(Shop shop);
    }

    public void setOnClickListener(setOnClickListener listener) {
        this.onClickListener = listener;
    }

}