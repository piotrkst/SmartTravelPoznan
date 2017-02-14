package com.piotrkostecki.smarttravelpoznan.presentation.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.PekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;
import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Constants;
import com.piotrkostecki.smarttravelpoznan.presentation.presenter.TimetableListPresenter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.CustomLayoutManager;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.TimetableAdapter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.TimetableListView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableListFragment extends BaseFragment implements TimetableListView {

    @Inject TimetableListPresenter timetableListPresenter;
    @Inject TimetableAdapter timetableAdapter;

    @BindView(R.id.tv_bollard_name) TextView tv_bollard_name;
    @BindView(R.id.rv_timetable) RecyclerView rv_timetable;

    public TimetableListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PekaComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_timetable_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("test", "onViewCreated: here");
        this.timetableListPresenter.setView(this);
        this.loadTimetableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.timetableListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.timetableListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.rv_timetable.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.timetableListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showLoading() {

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
    public void renderTimetableList(TimetableModel timetables) {
        if (timetables != null) {
            this.tv_bollard_name.setText(timetables.getBollardInfo().getName());
            this.timetableAdapter.setTimetableCollection(timetables);
        }
    }

    @Override
    public void viewTimetable(TimetableModel timetableModel) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public String getBollardSymbol() {
        return getActivity().getIntent().getExtras().getString(Constants.BOLLARD_SYMBOL);
    }

    @Override
    public Context context() {
        return null;
    }

    private void setupRecyclerView() {
        this.rv_timetable.setLayoutManager(new CustomLayoutManager(context()));
        this.rv_timetable.setAdapter(timetableAdapter);
    }

    /**
     * Load all timetables.
     * */
    private void loadTimetableView() {
        this.timetableListPresenter.initialize();
    }
}
