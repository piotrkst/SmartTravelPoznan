package com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components;

import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules.ActivityModule;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.modules.PekaModule;
import com.piotrkostecki.smarttravelpoznan.presentation.view.fragment.MainFragment;
import com.piotrkostecki.smarttravelpoznan.presentation.view.fragment.TimetableFragment;

import dagger.Component;

/**
 * A scope {@link com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity} component.
 * Injects specific Views.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PekaModule.class})
public interface PekaComponent extends ActivityComponent {
    void inject(MainFragment mainFragment);
    void inject(TimetableFragment timetableFragment);
}
