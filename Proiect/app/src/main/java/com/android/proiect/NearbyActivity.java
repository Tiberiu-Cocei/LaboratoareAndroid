package com.android.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.proiect.Adapters.CatalogListAdapter;
import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Catalog;
import com.android.proiect.Database.Entities.Shop;

import java.util.ArrayList;
import java.util.List;

public class NearbyActivity extends AppCompatActivity {

    private ListView catalogListView;

    private AppDatabase appDatabase;

    private Context context;

    private TextView shopDescription;

    private TextView shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        appDatabase = AppDatabase.getInstance(this);
        context = this;
        shopDescription = findViewById(R.id.description);
        shopAddress = findViewById(R.id.address);

        final ArrayList<String> shopNames = getNearbyShopNames();
        Spinner categorySpinner = findViewById(R.id.shopSpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shopNames);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Shop shop = appDatabase.shopDao().getByShopName(shopNames.get(position));
                if(shop != null) {
                    List<Integer> cartIds = appDatabase.cartDao().getCartIds();
                    List<Catalog> catalogs = appDatabase.catalogDao().getAllByProductAndShop(cartIds, shop.id);
                    shopDescription.setText(shop.description);
                    shopAddress.setText(shop.address);
                    CatalogListAdapter catalogListAdapter = new CatalogListAdapter(context, R.layout.catalog_list_view, catalogs);
                    catalogListView = findViewById(R.id.catalogList);
                    catalogListView.setAdapter(catalogListAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private ArrayList<String> getNearbyShopNames() { //TODO: delete a zero for both numbers to show second shop
        List<Shop> shops = appDatabase.shopDao().getAll();
        ArrayList<String> shopsToReturn = new ArrayList<>();
        for(Shop shop : shops) {
            if(Math.abs(shop.xCoordinate - MainActivity.longitude) < 0.00225 &&
                    Math.abs(shop.yCoordinate - MainActivity.latitude) < 0.0025)
                shopsToReturn.add(shop.shopName);
        }
        return shopsToReturn;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.products:
                openProductsMenu();
                return true;
            case R.id.cart:
                openCartMenu();
                return true;
            case R.id.catalogs:
                openCatalogMenu();
                return true;
            case R.id.nearby:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCartMenu() {
        Intent intent = new Intent(this, CartActivity.class);
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
