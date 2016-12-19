package com.piotrkostecki.smarttravelpoznan.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.UIThread;
import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Navigator;

import java.util.Timer;

import javax.inject.Inject;

import static java.lang.Thread.sleep;

/**
 * This is the app entry point.
 */
public class SplashActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: Wait specific amount of time, then run main activity
        this.navigator.navigateToMainActivity(this);
        finish();
    }
}
