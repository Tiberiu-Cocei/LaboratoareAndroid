package com.android.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.proiect.Adapters.CartListAdapter;
import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Cart;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    public ListView cartListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartList);
        generateList(this, cartListView);
    }

    public static void generateList(Context context, ListView cartListView) {
        List<Cart> cartList = AppDatabase.getInstance(context).cartDao().getAll();
        CartListAdapter cartListAdapter = new CartListAdapter(context, R.layout.cart_list_view, cartList, cartListView);
        cartListView.setAdapter(cartListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.products:
                openProductsMenu();
                return true;
            case R.id.cart:
                return true;
            case R.id.catalogs:
                openCatalogMenu();
                return true;
            case R.id.nearby:
                openNearbyMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openNearbyMenu() {
        Intent intent = new Intent(this, NearbyActivity.class);
        startActivity(intent);
    }

    public void openProductsMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openCatalogMenu() {
        Intent intent = new Intent(this, CatalogActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
