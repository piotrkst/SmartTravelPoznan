package com.piotrkostecki.smarttravelpoznan.presentation.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.PekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;
import com.piotrkostecki.smarttravelpoznan.presentation.presenter.MainPresenter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.MainView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment implements MainView {

    public interface DirectionSelectedListener {
        void navigateToTimetable();
    }

    @Inject MainPresenter mainPresenter;

    private DirectionSelectedListener directionSelectedListener;

    public MainFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DirectionSelectedListener) {
            this.directionSelectedListener = (DirectionSelectedListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PekaComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainPresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mainPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mainPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mainPresenter.destroy();
    }

    @OnClick(R.id.btn_getDirections)
    public void getDirections() {
        Log.i("clicked", "getDirections: clicked");
        mainPresenter.getDirectionList();
    }

    @Override
    public void showDirections() {

    }

    @Override
    public void hideDirections() {

    }

    @Override
    public void viewTimetable() {
        this.directionSelectedListener.navigateToTimetable();
    }

    @Override
    public void renderDirectionList(Collection<DirectionModel> directionCollection) {

    }

    @Override
    public void showLoading() {
        Log.i("test'", "showLoading: loading");
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
