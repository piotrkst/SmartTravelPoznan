package com.piotrkostecki.smarttravelpoznan.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import com.piotrkostecki.smarttravelpoznan.presentation.AndroidApplication;
import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.ApplicationComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules.ActivityModule;
import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Navigator;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends Activity {

    @Inject Navigator navigator;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected void setupToolbar(String title) {
        toolbar.setTitle(title);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
