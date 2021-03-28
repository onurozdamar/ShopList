package com.example.shoplist.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Basket {

    @PrimaryKey(autoGenerate = true)
    private int basketId;

    private int shopId;

    private String productName;

    private String productCount;

    private boolean checked;

    public Basket(int shopId, String productName, String productCount, boolean checked) {
        this.shopId = shopId;
        this.productName = productName;
        this.productCount = productCount;
        this.checked = checked;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public int getBasketId() {
        return basketId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCount() {
        return productCount;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

