package com.android.proiect.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.proiect.Database.Daos.*;
import com.android.proiect.Database.Entities.*;

@Database(entities = {Category.class, Product.class, Shop.class, Catalog.class, Cart.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "proiect.db";

    private static AppDatabase instance = null;

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract ShopDao shopDao();

    public abstract CatalogDao catalogDao();

    public abstract CartDao cartDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            initializeDatabase();
        }
        return instance;
    }

    private static void initializeDatabase() {
        if(instance.categoryDao().getAll().size() == 0) {
            instance.categoryDao().insert(new Category(1, "Electronics"));
            instance.categoryDao().insert(new Category(2, "Clothing"));
            instance.categoryDao().insert(new Category(3, "Food"));
        }

        if(instance.productDao().getAll().size() == 0) {
            instance.productDao().insert(new Product(1, 1, "Samsung Phone M30",
                    "New and reliable phone by Samsung", 1499.99));
            instance.productDao().insert(new Product(2, 1, "Lenovo Lgion X100",
                    "Gaming laptop by Lenovo", 4200.00));
            instance.productDao().insert(new Product(3, 1, "Nvidia GTX 1060",
                    "Nvidia Graphics Card", 815.99));
            instance.productDao().insert(new Product(4, 1, "IPhone 9S",
                    "Expensive phone by Apple", 1999.99));
            instance.productDao().insert(new Product(5, 1, "Toaster MK300",
                    "Toaster with a window", 149.49));

            instance.productDao().insert(new Product(6, 2, "TShirt",
                    "Bland black TShirt, size M", 24.99));
            instance.productDao().insert(new Product(7, 2, "Winter Coat",
                    "Dark blue winter coat, size L", 249.49));
            instance.productDao().insert(new Product(8, 2, "Hoodie",
                    "White Hoodie, size M", 84.49));
            instance.productDao().insert(new Product(9, 2, "Gym Gloves",
                    "Gym gloves, fingerless", 35.99));
            instance.productDao().insert(new Product(10, 2, "Shoes",
                    "Brown shoes, size 43", 219.49));

            instance.productDao().insert(new Product(11, 3, "Neopolitan Icecream",
                    "Tricolor icecream made by X", 13.99));
            instance.productDao().insert(new Product(12, 3, "Crackers",
                    "Pack with 16 packs of crackers", 5.49));
            instance.productDao().insert(new Product(13, 3, "Chicken meat",
                    "1KG of boneless chicken meat", 21.49));
            instance.productDao().insert(new Product(14, 3, "Burgers",
                    "Pack with 4 ready to cook burgers", 15.99));
            instance.productDao().insert(new Product(15, 3, "Frozen Pizza",
                    "Frozen pizza that can be easily cooked in the oven", 11.99));
        }

        if(instance.shopDao().getAll().size() == 0) {
            instance.shopDao().insert(new Shop(1, "Auchan", "Palas mall supermarket",
                    "Palas Street Nr 5C", 47.155293, 27.588450));
            instance.shopDao().insert(new Shop(2, "Lidl", "Bucium supermarket",
                    "Bucium 9X", 47.113730, 27.632851));
            instance.shopDao().insert(new Shop(3, "Minimarket", "Bucium minimarket",
                    "Bucium 8X", 47.117032, 27.632164));
        }

        if(instance.catalogDao().getAll().size() == 0) {
            instance.catalogDao().insert(new Catalog(1, 1, 1));
            instance.catalogDao().insert(new Catalog(2, 2, 1));
            instance.catalogDao().insert(new Catalog(3, 3, 1));
            instance.catalogDao().insert(new Catalog(4, 4, 1));
            instance.catalogDao().insert(new Catalog(5, 6, 1));
            instance.catalogDao().insert(new Catalog(6, 7, 1));
            instance.catalogDao().insert(new Catalog(7, 11, 1));
            instance.catalogDao().insert(new Catalog(8, 13, 1));
            instance.catalogDao().insert(new Catalog(9, 14, 1));
            instance.catalogDao().insert(new Catalog(10, 15, 1));

            instance.catalogDao().insert(new Catalog(11, 5, 2));
            instance.catalogDao().insert(new Catalog(12, 6, 2));
            instance.catalogDao().insert(new Catalog(13, 9, 2));
            instance.catalogDao().insert(new Catalog(14, 11, 2));
            instance.catalogDao().insert(new Catalog(15, 12, 2));
            instance.catalogDao().insert(new Catalog(16, 13, 2));
            instance.catalogDao().insert(new Catalog(17, 14, 2));
            instance.catalogDao().insert(new Catalog(18, 15, 2));

            instance.catalogDao().insert(new Catalog(19, 6, 3));
            instance.catalogDao().insert(new Catalog(20, 8, 3));
            instance.catalogDao().insert(new Catalog(21, 11, 3));
            instance.catalogDao().insert(new Catalog(22, 12, 3));
            instance.catalogDao().insert(new Catalog(23, 15, 3));
        }
    }
}
