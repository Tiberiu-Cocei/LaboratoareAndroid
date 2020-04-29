package com.android.proiect.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop")
public class Shop {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "shop_name")
    public String shopName;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "x_coordinate")
    public double xCoordinate;

    @ColumnInfo(name = "y_coordinate")
    public double yCoordinate;

    public Shop(int id, String shopName, String description, String address, double xCoordinate, double yCoordinate) {
        this.id = id;
        this.shopName = shopName;
        this.description = description;
        this.address = address;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}
