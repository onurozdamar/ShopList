package com.example.shoplist.DataAccess;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shoplist.AppDatabase;
import com.example.shoplist.Entities.Basket;

import java.util.List;

public class BasketRepository {
    private BasketDao basketDao;
    private LiveData<List<Basket>> allBaskets;

    public BasketRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        basketDao = database.basketDao();
        allBaskets = basketDao.getAllBaskets();
    }

    public void insert(Basket basket) {
        new BasketAsyncTask(new Invocation() {
            public void proceed(Basket basket) {
                basketDao.insert(basket);
            }
        }).execute(basket);
    }

    public void update(Basket basket) {
        new BasketAsyncTask(new Invocation() {
            public void proceed(Basket basket) {
                basketDao.update(basket);
            }
        }).execute(basket);
    }

    public void delete(Basket basket) {
        new BasketAsyncTask(new Invocation() {
            public void proceed(Basket basket) {
                basketDao.delete(basket);
            }
        }).execute(basket);
    }

    public void deleteAllBaskets() {
        new DeleteAllBasketAsyncTask(basketDao).execute();
    }

    public LiveData<List<Basket>> getAllBaskets() {
        return allBaskets;
    }

    public LiveData<List<Basket>> getAllBaskets(int shopId) {
        return basketDao.getAllBaskets(shopId);
    }

    private interface Invocation {
        void proceed(Basket basket);
    }

    private static class BasketAsyncTask extends AsyncTask<Basket, Void, Void> {
        private Invocation invocation;

        private BasketAsyncTask(Invocation invocation) {
            this.invocation = invocation;
        }

        @Override
        protected Void doInBackground(Basket... baskets) {
            invocation.proceed(baskets[0]);
            return null;
        }
    }

    private static class DeleteAllBasketAsyncTask extends AsyncTask<Void, Void, Void> {
        private BasketDao basketDao1;

        private DeleteAllBasketAsyncTask(BasketDao basketDao1) {
            this.basketDao1 = basketDao1;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            basketDao1.deleteAllBaskets();
            return null;
        }
    }
}
