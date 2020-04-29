package com.android.proiect.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.proiect.Database.Entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE categoryId = (:categoryId)")
    List<Product> getAllByCategoryId(int categoryId);

    @Query("SELECT * FROM product WHERE id = (:id)")
    Product getById(int id);

    @Insert
    void insert(Product product);
}
