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

import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private ListView catalogListView;

    private AppDatabase appDatabase;

    private Context context;

    private TextView shopDescription;

    private TextView shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        appDatabase = AppDatabase.getInstance(this);
        context = this;
        shopDescription = findViewById(R.id.description);
        shopAddress = findViewById(R.id.address);

        List<String> shopNames = appDatabase.shopDao().getShopNames();
        Spinner categorySpinner = findViewById(R.id.shopSpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shopNames);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Shop shop = appDatabase.shopDao().getByShopId(position + 1);
                List<Catalog> catalogs = appDatabase.catalogDao().getAllByShopId(position + 1);
                shopDescription.setText(shop.description);
                shopAddress.setText(shop.address);
                CatalogListAdapter catalogListAdapter = new CatalogListAdapter(context, R.layout.catalog_list_view, catalogs);
                catalogListView = findViewById(R.id.catalogList);
                catalogListView.setAdapter(catalogListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
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

    public void openCartMenu() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
