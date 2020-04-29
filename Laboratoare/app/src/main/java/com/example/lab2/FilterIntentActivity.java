package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FilterIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.catalog:
                openCatalogMenu();
                return true;
            case R.id.cart:
                openCartMenu();
                return true;
            case R.id.account:
                openAccountMenu();
                return true;
            case R.id.settings:
                openSettingsMenu();
                return true;
            case R.id.sensor:
                openSensorMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSensorMenu() {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    public void openSettingsMenu() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openCatalogMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openCartMenu() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void openAccountMenu() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
