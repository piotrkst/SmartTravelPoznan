package com.piotrkostecki.smarttravelpoznan.presentation.view.activity;

import android.os.Bundle;

import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Activity created to manage A-B tests.
 */
public class FlowActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navigator.navigateToSplashActivity(this);
        finish();
    }
}
