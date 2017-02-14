package com.piotrkostecki.smarttravelpoznan.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.piotrkostecki.smarttravelpoznan.presentation.view.activity.TimetableActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.view.activity.MainActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.view.activity.SplashActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        // empty
    }

    /**
     * Goes to the specific activity screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToMainActivity(final Context context) {
        if (context != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
    public void navigateToSplashActivity(Context context) {
        if (context != null) {
            Intent intentToLaunch = SplashActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
    public void navigateToTimetableActivity(Context context, String bollardSymbol) {
        if (context != null) {
            Intent intentToLaunch = TimetableActivity.getCallingIntent(context);
            intentToLaunch.putExtra(Constants.BOLLARD_SYMBOL, bollardSymbol);
            context.startActivity(intentToLaunch);
        }
    }
}
