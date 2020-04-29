package com.android.proiect.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.proiect.Database.Entities.Catalog;

import java.util.List;

@Dao
public interface CatalogDao {
    @Query("SELECT * FROM catalog")
    List<Catalog> getAll();

    @Query("SELECT * FROM catalog WHERE shopId = (:shopId)")
    List<Catalog> getAllByShopId(int shopId);

    @Query("SELECT * FROM catalog WHERE productId IN (:productIds) AND shopId =(:shopId)")
    List<Catalog> getAllByProductAndShop(List<Integer> productIds, int shopId);

    @Insert
    void insert(Catalog catalog);
}
