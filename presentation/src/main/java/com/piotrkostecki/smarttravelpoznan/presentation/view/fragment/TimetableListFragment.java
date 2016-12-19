package com.piotrkostecki.smarttravelpoznan.presentation.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;
import com.piotrkostecki.smarttravelpoznan.presentation.presenter.TimetableListPresenter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.TimetableAdapter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.TimetableLayoutManager;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.TimetableListView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TimetableListFragment extends BaseFragment implements TimetableListView {

    @Inject TimetableListPresenter timetableListPresenter;
    @Inject TimetableAdapter timetableAdapter;

    @BindView(R.id.rv_timetable) RecyclerView rv_timetable;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_retry) RelativeLayout rl_retry;
    @BindView(R.id.btn_retry) Button bt_retry;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void renderTimetableList(Collection<DirectionModel> directionModelCollection) {

    }

    @Override
    public void viewTimetable(DirectionModel directionModel) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

    private void setupRecyclerView() {
        this.timetableAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_timetable.setLayoutManager(new TimetableLayoutManager(context()));
        this.rv_timetable.setAdapter(timetableAdapter);
    }

    /**
     * Load all timetables.
     * */
    private void loadTimetableView() {
        this.timetableListPresenter.initialize();
    }

    @OnClick(R.id.btn_retry) void onButtonRetryClick() {
        TimetableListFragment.this.loadTimetableView();
    }

    private TimetableAdapter.OnItemClickListener onItemClickListener = new TimetableAdapter.OnItemClickListener() {
        @Override public void onTimetableItemClicked(DirectionModel directionModel) {
            if (TimetableListFragment.this.timetableListPresenter != null && directionModel != null) {
                TimetableListFragment.this.timetableListPresenter.onTimetableClicked(directionModel);
            }
        }
    };
}
