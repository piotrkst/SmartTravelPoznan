package com.piotrkostecki.smarttravelpoznan.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.HasComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.DaggerPekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.PekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.view.fragment.TimetableListFragment;

/**
 * Activity for displaying timetables
 * */
public class TimetableActivity extends BaseActivity implements HasComponent<PekaComponent> {

    String TAG = "test";
    private PekaComponent pekaComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, TimetableActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new TimetableListFragment());
        }
    }

    private void initializeInjector() {
        this.pekaComponent = DaggerPekaComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public PekaComponent getComponent() {
        return pekaComponent;
    }
}
