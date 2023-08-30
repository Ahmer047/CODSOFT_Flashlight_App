package com.example.flash_light;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ImageButton Ibtn;
    TextView word;
    boolean cameraflash = false; //in which device our app is installed has the camera flash or not
    boolean flashOn = false; //state of flash on or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ibtn = findViewById(R.id.btn);

        word = findViewById(R.id.textView);
        word.setPaintFlags(word.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        cameraflash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        Ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cameraflash){
                    if (flashOn){
                        flashOn = false;
                        Ibtn.setImageResource(R.drawable.p_on);
                        try {
                            flashlightOff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        flashOn = true;
                        Ibtn.setImageResource(R.drawable.p_off);
                        try {
                            flashlightOn();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Flash is not supported in this device", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void flashlightOn() throws CameraAccessException{
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(MainActivity.this, "Flashlight ON !", Toast.LENGTH_SHORT).show();
    }

    private void flashlightOff() throws CameraAccessException{
        CameraManager cameramanager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameramanager != null;
        String cameraId = cameramanager.getCameraIdList()[0];
        cameramanager.setTorchMode(cameraId, false);
        Toast.makeText(MainActivity.this, "Flashlight OFF !", Toast.LENGTH_SHORT).show();
    }
}