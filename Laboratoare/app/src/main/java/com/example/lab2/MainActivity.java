package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView ItemListView;
    private TextView ItemDescription;
    private String[] ItemList = new String[] {"Banana", "Apple", "Tomato", "Lettuce", "Lemon", "Spaghetti", "Sauce", "Ketchup", "Mustard", "Biscuits", "Mackerel", "Mayo", "Salt", "Pepper"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemListView = findViewById(R.id.list_view);
        ItemDescription = findViewById(R.id.textView3);

        if(savedInstanceState != null) {
            ItemDescription.setText(savedInstanceState.getCharSequence("ItemDescription"));
        }

        Log.d("Lifecycle", "On Create called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                openSettingsMenu();
                return true;
            case R.id.catalog:
                return true;
            case R.id.cart:
                openCartMenu();
                return true;
            case R.id.account:
                openAccountMenu();
                return true;
            case R.id.sensor:
                openSensorMenu();
                return true;
            case R.id.camera:
                openCameraMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCameraMenu(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void openSensorMenu() {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    public void openSettingsMenu() {
        Intent intent = new Intent(this, SettingsActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        List<String> itemListArrayList = new ArrayList<>(Arrays.asList(ItemList));
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<>
                (MainActivity.this, android.R.layout.simple_expandable_list_item_1, itemListArrayList);
        ItemListView.setAdapter(itemListAdapter);

        ItemListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemDescription.setText(ItemList[i]);
                    }
                }
        );

        Log.d("Lifecycle", "On Start called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Lifecycle", "On Resume called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Lifecycle", "On Pause called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Lifecycle", "On Stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Lifecycle", "On Destroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putCharSequence("ItemDescription", ItemDescription.getText());
    }
}
