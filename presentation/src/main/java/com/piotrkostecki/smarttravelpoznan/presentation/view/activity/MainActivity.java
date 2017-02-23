package com.piotrkostecki.smarttravelpoznan.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.HasComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.DaggerPekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.PekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.BollardAdapter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.component.DialogBollards;
import com.piotrkostecki.smarttravelpoznan.presentation.view.fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main application screen.
 */
public class MainActivity extends BaseActivity implements HasComponent<PekaComponent>,
        MainFragment.DirectionSelectedListener {

    private PekaComponent pekaComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar(getString(R.string.app_name));

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new MainFragment());
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

    @Override
    public void navigateToTimetable(String bollardSymbol) {
        navigator.navigateToTimetableActivity(this, bollardSymbol);
    }
}
