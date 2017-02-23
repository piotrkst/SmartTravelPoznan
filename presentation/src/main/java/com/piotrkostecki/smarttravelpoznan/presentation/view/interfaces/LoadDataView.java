package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    Context context();
}
