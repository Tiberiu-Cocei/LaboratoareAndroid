package com.android.proiect.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.proiect.Database.Entities.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart")
    List<Cart> getAll();

    @Query("SELECT MAX(id) FROM cart")
    Integer getMaxId();

    @Query("SELECT quantity FROM cart WHERE productId = (:productId)")
    Integer getQuantityForProduct(int productId);

    @Query("UPDATE cart SET quantity = (:quantity) WHERE productId = (:productId)")
    void update(int quantity, int productId);

    @Query("SELECT productId FROM cart")
    List<Integer> getCartIds();

    @Insert
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);
}
