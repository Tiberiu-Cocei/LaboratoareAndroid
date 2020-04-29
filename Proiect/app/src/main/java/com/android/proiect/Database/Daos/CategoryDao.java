package com.android.proiect.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.proiect.Database.Entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT category_name FROM category")
    List<String> getCategoryNames();

    @Insert
    void insert(Category category);
}
