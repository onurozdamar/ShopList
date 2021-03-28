package com.example.shoplist.DataAccess;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.shoplist.Entities.Basket;
import java.util.List;

@Dao
public interface BasketDao {
    @Insert
    void insert(Basket basket);

    @Update
    void update(Basket basket);

    @Delete
    void delete(Basket basket);

    @Query("DELETE  FROM basket")
    void deleteAllBaskets();

    @Query("SELECT * FROM basket")
    LiveData<List<Basket>> getAllBaskets();

    @Query("SELECT * FROM basket where shopId = :shopId")
    LiveData<List<Basket>> getAllBaskets(int shopId);
}
