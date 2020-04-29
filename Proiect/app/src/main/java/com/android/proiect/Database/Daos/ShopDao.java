package com.android.proiect.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.proiect.Database.Entities.Shop;

import java.util.List;

@Dao
public interface ShopDao {
    @Query("SELECT * FROM shop")
    List<Shop> getAll();

    @Query("SELECT shop_name FROM shop")
    List<String> getShopNames();

    @Query("SELECT * FROM shop WHERE id = (:id)")
    Shop getByShopId(int id);

    @Query("SELECT * FROM shop WHERE shop_name = (:shopName)")
    Shop getByShopName(String shopName);

    @Insert
    void insert(Shop shop);
}
