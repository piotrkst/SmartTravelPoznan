package com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components;

import android.content.Context;

import com.piotrkostecki.smarttravelpoznan.domain.executor.PostExecutionThread;
import com.piotrkostecki.smarttravelpoznan.domain.executor.ThreadExecutor;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules.ApplicationModule;
import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Navigator;
import com.piotrkostecki.smarttravelpoznan.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    // Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
}
