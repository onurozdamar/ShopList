package com.example.shoplist.DataAccess;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shoplist.AppDatabase;
import com.example.shoplist.Entities.Shop;

import java.util.List;

public class ShopRepository {
    private ShopDao shopDao;
    private LiveData<List<Shop>> allShops;

    public ShopRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        shopDao = database.shopDao();
        allShops = shopDao.getAllShops();
    }

    public void insert(Shop shop) {
        new ShopAsyncTask(new Invocation() {
            public void proceed(Shop shop) {
                shopDao.insert(shop);
            }
        }).execute(shop);
    }

    public void update(Shop shop) {
        new ShopAsyncTask(new Invocation() {
            public void proceed(Shop shop) {
                shopDao.update(shop);
            }
        }).execute(shop);
    }

    public void delete(Shop shop) {
        new ShopAsyncTask(new Invocation() {
            public void proceed(Shop shop) {
                shopDao.delete(shop);
            }
        }).execute(shop);
    }

    public void deleteAllShops() {
        new DeleteAllShopAsyncTask(shopDao).execute();
    }

    public LiveData<List<Shop>> getAllShops() {
        return allShops;
    }

    private interface Invocation {
        void proceed(Shop shop);
    }

    private static class ShopAsyncTask extends AsyncTask<Shop, Void, Void> {
        private Invocation invocation;

        private ShopAsyncTask(Invocation invocation) {
            this.invocation = invocation;
        }

        @Override
        protected Void doInBackground(Shop... shops) {
            invocation.proceed(shops[0]);
            return null;
        }
    }

    private static class DeleteAllShopAsyncTask extends AsyncTask<Void, Void, Void> {
        private ShopDao shopDao1;

        private DeleteAllShopAsyncTask(ShopDao shopDao1) {
            this.shopDao1 = shopDao1;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            shopDao1.deleteAllShops();
            return null;
        }
    }
}
