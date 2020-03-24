package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mLight;
    private Sensor mField;

    private TextView xAxisAngularSpeed;
    private TextView yAxisAngularSpeed;
    private TextView zAxisAngularSpeed;
    private TextView xAxisAcceleration;
    private TextView yAxisAcceleration;
    private TextView zAxisAcceleration;
    private TextView LightLevel;
    private TextView Longitude;
    private TextView Latitude;

    private LocationManager LocationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Longitude.setText(String.valueOf(location.getLongitude()));
            Latitude.setText(String.valueOf(location.getLatitude()));
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
        setContentView(R.layout.activity_sensor);

        xAxisAngularSpeed = findViewById(R.id.xAngularSpeed);
        yAxisAngularSpeed = findViewById(R.id.yAngularSpeed);
        zAxisAngularSpeed = findViewById(R.id.zAngularSpeed);
        xAxisAcceleration = findViewById(R.id.xAcceleration);
        yAxisAcceleration = findViewById(R.id.yAcceleration);
        zAxisAcceleration = findViewById(R.id.zAcceleration);
        LightLevel = findViewById(R.id.lightLevel);
        Longitude = findViewById(R.id.longitude);
        Latitude = findViewById(R.id.latitude);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_NORMAL);

        LocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1;

        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION
            );
        }

        LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            xAxisAngularSpeed.setText(String.valueOf(event.values[0]));
            yAxisAngularSpeed.setText(String.valueOf(event.values[1]));
            zAxisAngularSpeed.setText(String.valueOf(event.values[2]));
        }
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAxisAcceleration.setText(String.valueOf(event.values[0]));
            yAxisAcceleration.setText(String.valueOf(event.values[1]));
            zAxisAcceleration.setText(String.valueOf(event.values[2]));
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            LightLevel.setText(String.valueOf(event.values[0]));
        }
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
                openCatalogMenu();
                return true;
            case R.id.cart:
                openCartMenu();
                return true;
            case R.id.account:
                openAccountMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCatalogMenu() {
        Intent intent = new Intent(this, MainActivity.class);
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
}
