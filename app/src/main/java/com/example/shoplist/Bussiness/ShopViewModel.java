package com.example.shoplist.Bussiness;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.shoplist.DataAccess.ShopRepository;
import com.example.shoplist.Entities.Shop;
import java.util.List;

public class ShopViewModel extends AndroidViewModel {

    private ShopRepository repository;
    private LiveData<List<Shop>> allShops;

    public ShopViewModel(@NonNull Application application) {
        super(application);
        repository = new ShopRepository(application);
        allShops = repository.getAllShops();
    }

    public void insert(Shop shop) {
        repository.insert(shop);
    }

    public void update(Shop shop) {
        repository.update(shop);
    }

    public void delete(Shop shop) {
        repository.delete(shop);
    }

    public void deleteAllShops() {
        repository.deleteAllShops();
    }

    public LiveData<List<Shop>> getAllShops() {
        return allShops;
    }
}
