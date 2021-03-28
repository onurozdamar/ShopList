package com.example.shoplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoplist.Bussiness.BasketViewModel;
import com.example.shoplist.Entities.Basket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CreateBasket extends AppCompatActivity {


    private BasketViewModel basketViewModel;
    public static boolean isEditing;
    private Basket basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_basket);


        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BasketAdapter adapter = new BasketAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        final int shopId = intent.getIntExtra("shopId", 1);

        basketViewModel = ViewModelProviders.of(this).get(BasketViewModel.class);
        basketViewModel.getAllBaskets(shopId).observe(this, new Observer<List<Basket>>() {
            @Override
            public void onChanged(List<Basket> baskets) {
                adapter.submitList(baskets);
            }
        });

        final FloatingActionButton buttonAddBasket = findViewById(R.id.button_add_basket);
        buttonAddBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) { // update durumu
                    isEditing = false;
                    buttonAddBasket.setImageResource(R.drawable.ic_add_black_24dp);
                    buttonAddBasket.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAdd)));
                    BasketAdapter.BasketHolder basketHolder = adapter.getBasketHolder();
                    basketHolder.setVisiblesToShowState();
                    String productName = basketHolder.getProductName();
                    String productCount = basketHolder.getProductCount();
                    if (productName.matches("") || productCount.matches("")) return;
                    boolean checked = basketHolder.isChecked();
                    int id = basket.getBasketId();
                    Basket newBasket = new Basket(shopId, productName, productCount, checked);
                    newBasket.setBasketId(id);
                    basketViewModel.update(newBasket);
                } else { // add durumu
                    Basket basket = new Basket(shopId, "ürün adı", "ürün adedi", false);
                    basketViewModel.insert(basket);
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
                basketViewModel.delete(adapter.getBasketAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CreateBasket.this, "Basket deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnLongClickListener(new BasketAdapter.setOnLongClickListener() {
            @Override
            public void onItemLongClick(Basket basket1) {
                if (!isEditing) {
                    buttonAddBasket.setImageResource(R.drawable.ic_check_black_24dp);
                    buttonAddBasket.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorSave)));
                    basket = basket1;
                }
            }
        });

        adapter.setOnCheckBoxClickListener(new BasketAdapter.setOnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(Basket basket) {
                basket.setChecked(!basket.getChecked());
                basketViewModel.update(basket);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isEditing = false;
    }
}
