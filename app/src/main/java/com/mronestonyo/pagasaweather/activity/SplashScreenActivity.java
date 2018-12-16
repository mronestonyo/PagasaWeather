package com.mronestonyo.pagasaweather.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mronestonyo.pagasaweather.R;
import com.mronestonyo.pagasaweather.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashScreenActivity extends BaseActivity {

    private int SPLASH_TIME_OUT = 2000;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /**
         * On a post-Android 6.0 devices, check if the required permissions have
         * been granted.
         */
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("antonhttp", " SDK INT >= 23");
            checkPermissions();
        } else {
            Log.d("antonhttp", "ELSE SDK INT >= 23");
            proceedToMain();
        }

        ImageView imgSplashScreen = findViewById(R.id.imgSplashScreen);
        Glide.with(getViewContext())
                .load(R.drawable.img_splash_screen_logo)
                .into(imgSplashScreen);
    }

    /**
     * Check if the required permissions have been granted, and
     * {@link #proceedToMain()} if they have. Otherwise
     * {@link #requestPermissions(String[], int)}.
     */
    private void checkPermissions() {

        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                proceedToMain();
                break;
        }

    }

    private void proceedToMain() {
        new Handler().postDelayed(() -> {
            Bundle bundle = new Bundle();
            MainActivity.start(getViewContext(), 0, null);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
