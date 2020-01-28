package com.kanchan.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class MainActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    private String mCameraId;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!isFlashAvailable)
        {
            showNoFlashError();
        }


        mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        }
        catch (CameraAccessException e)
        {
            e.printStackTrace();
        }
        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            private CompoundButton buttonView;
            private boolean isChecked;

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                this.buttonView = buttonView;
                this.isChecked = isChecked;
                switchFlashLight(isChecked);
            }
        });
    }
    public void showNoFlashError()
    {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("oops!");
        alert.setMessage("FLASH NOT AVAILABLE IN THIS DEVICE");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }
    @RequiresApi(api=Build.VERSION_CODES.M)
    public void switchFlashLight(boolean status)
    {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        }
        catch (CameraAccessException e)
        {
            e.printStackTrace();
        }
    }
}
