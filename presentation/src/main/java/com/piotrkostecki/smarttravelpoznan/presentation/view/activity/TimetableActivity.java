package com.piotrkostecki.smarttravelpoznan.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Activity for displaying timetables
 * */
public class TimetableActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TimetableActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
