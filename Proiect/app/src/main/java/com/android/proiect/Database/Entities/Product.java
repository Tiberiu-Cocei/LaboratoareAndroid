package com.android.proiect.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey
    public int id;

    @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id")
    public int categoryId;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "price")
    public Double price;

    public Product(int id, int categoryId, String productName, String description, Double price) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}
