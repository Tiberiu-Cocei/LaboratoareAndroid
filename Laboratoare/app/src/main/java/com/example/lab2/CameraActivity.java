package com.example.lab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    TextView imagePath;

    Camera.PictureCallback jpegCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int MY_PERMISSION_CAMERA = 1;

        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String [] { android.Manifest.permission.CAMERA },
                    MY_PERMISSION_CAMERA
            );
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        imagePath = findViewById(R.id.pathView);
        surfaceHolder.addCallback(this);

        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                    File mypath = new File(directory,System.currentTimeMillis() + "");
                    outStream = new FileOutputStream(mypath);
                    outStream.write(data);
                    imagePath.setText(mypath.getAbsolutePath());
                    Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_LONG).show();
                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshCamera();
            }
        };
    }

    public void captureImage(View v) {
        camera.takePicture(null, null, jpegCallback);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
        } catch (Exception e) {
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        List<Camera.Size> sizes = param.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        param.setPreviewSize(selected.width, selected.height);
        camera.setParameters(param);
        try {
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) { }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) { }
    }
}