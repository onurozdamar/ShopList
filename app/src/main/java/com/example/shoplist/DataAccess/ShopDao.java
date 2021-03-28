package com.example.shoplist.DataAccess;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoplist.Entities.Basket;
import com.example.shoplist.Entities.Shop;

import java.util.List;

@Dao
public interface ShopDao {
    @Insert
    void insert(Shop shop);

    @Update
    void update(Shop shop);

    @Delete
    void delete(Shop shop);

    @Query("DELETE FROM shop")
    void deleteAllShops();

    @Query("SELECT * FROM shop")
    LiveData<List<Shop>> getAllShops();
}
