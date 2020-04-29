package com.android.proiect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.proiect.Adapters.ProductListAdapter;
import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView productListView;

    private AppDatabase appDatabase;

    private Context context;

    public static Double longitude;

    public static Double latitude;

    LocationManager LocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            longitude = 47.114577;
            latitude = 27.632415;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        context = this;

        LocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1;

        while (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION
            );
        }

        LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);

        List<String> categoryNames = appDatabase.categoryDao().getCategoryNames();
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                List<Product> products = appDatabase.productDao().getAllByCategoryId(position + 1);
                ProductListAdapter productListAdapter = new ProductListAdapter(context, R.layout.product_list_view, products);
                productListView = findViewById(R.id.productList);
                productListView.setAdapter(productListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        OneTimeWorkRequest locationWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
        WorkManager.getInstance(this).enqueue(locationWorkRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.products:
                return true;
            case R.id.cart:
                openCartMenu();
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

    public void openCartMenu() {
        Intent intent = new Intent(this, CartActivity.class);
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
