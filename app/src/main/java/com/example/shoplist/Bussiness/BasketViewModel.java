package com.example.shoplist.Bussiness;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.shoplist.DataAccess.BasketRepository;
import com.example.shoplist.Entities.Basket;
import java.util.List;

public class BasketViewModel extends AndroidViewModel {

    private BasketRepository repository;
    private LiveData<List<Basket>> allBaskets;

    public BasketViewModel(@NonNull Application application) {
        super(application);
        repository = new BasketRepository(application);
        allBaskets = repository.getAllBaskets();
    }

    public void insert(Basket basket) {
        repository.insert(basket);
    }

    public void update(Basket basket) {
        repository.update(basket);
    }

    public void delete(Basket basket) {
        repository.delete(basket);
    }

    public void deleteAllBaskets() {
        repository.deleteAllBaskets();
    }

    public LiveData<List<Basket>> getAllBaskets() {
        return allBaskets;
    }

    public LiveData<List<Basket>> getAllBaskets(int shopId) {
        return repository.getAllBaskets(shopId);
    }
}
