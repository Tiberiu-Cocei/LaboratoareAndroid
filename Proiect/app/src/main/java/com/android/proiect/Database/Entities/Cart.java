package com.android.proiect.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey
    public int id;

    @ForeignKey(entity = Product.class,
            parentColumns = "id",
            childColumns = "product_id")
    public int productId;

    @ColumnInfo(name = "quantity")
    public int quantity;

    public Cart(int id, int productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }
}
