package com.android.proiect.Database.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "catalog")
public class Catalog {
    @PrimaryKey
    public int id;

    @ForeignKey(entity = Product.class,
            parentColumns = "id",
            childColumns = "product_id")
    public int productId;

    @ForeignKey(entity = Shop.class,
            parentColumns = "id",
            childColumns = "shop_id")
    public int shopId;

    public Catalog(int id, int productId, int shopId) {
        this.id = id;
        this.productId = productId;
        this.shopId = shopId;
    }
}
