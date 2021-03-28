package com.example.shoplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoplist.Bussiness.ShopViewModel;
import com.example.shoplist.Entities.Shop;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShopList extends AppCompatActivity {

    private ShopViewModel shopViewModel;
    public static boolean isEditing;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_shop);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        final ShopAdapter adapter = new ShopAdapter();
        recyclerView.setAdapter(adapter);

        shopViewModel = ViewModelProviders.of(this).get(ShopViewModel.class);
        shopViewModel.getAllShops().observe(this, new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> shops) {
                adapter.submitList(shops);
            }
        });

        final FloatingActionButton buttonAddShop = findViewById(R.id.button_add_shop);
        buttonAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) { // update durumu
                    isEditing = false;
                    buttonAddShop.setImageResource(R.drawable.ic_add_black_24dp);
                    buttonAddShop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAdd)));
                    ShopAdapter.ShopHolder shopHolder = adapter.getShopHolder();
                    shopHolder.setVisiblesToShowState();
                    String shopName = shopHolder.getShopName();
                    if (shopName.matches("")) return;
                    int id = shop.getShopId();
                    Shop newShop = new Shop(shopName);
                    newShop.setShopId(id);
                    shopViewModel.update(newShop);
                } else { // add durumu
                    Shop newShop = new Shop("Alışveriş ismi");
                    shopViewModel.insert(newShop);
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                shopViewModel.delete(adapter.getShopAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ShopList.this, "Shop deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(new ShopAdapter.setOnClickListener() {
            @Override
            public void onItemClick(Shop shop) {
                Intent intent = new Intent(ShopList.this, CreateBasket.class);
                intent.putExtra("shopId", shop.getShopId());
                startActivity(intent);
            }
        });

        adapter.setOnLongClickListener(new ShopAdapter.setOnLongClickListener() {
            @Override
            public void onItemLongClick(Shop shop1) {
                if (!isEditing) {
                    buttonAddShop.setImageResource(R.drawable.ic_check_black_24dp);
                    buttonAddShop.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorSave)));
                    shop = shop1;
                }
            }
        });
    }

}
