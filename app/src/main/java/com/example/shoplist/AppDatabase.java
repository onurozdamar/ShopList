package com.example.shoplist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoplist.DataAccess.BasketDao;
import com.example.shoplist.DataAccess.ShopDao;
import com.example.shoplist.Entities.Basket;
import com.example.shoplist.Entities.Shop;

@Database(entities = {Shop.class, Basket.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShopDao shopDao();

    public abstract BasketDao basketDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "shop_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}