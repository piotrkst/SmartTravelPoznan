package com.piotrkostecki.smarttravelpoznan.presentation.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.PekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;
import com.piotrkostecki.smarttravelpoznan.presentation.navigation.Constants;
import com.piotrkostecki.smarttravelpoznan.presentation.presenter.TimetablePresenter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.ArrivalAdapter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.CustomLayoutManager;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.TimetableView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableFragment extends BaseFragment implements TimetableView {

    public interface BackClickedListener {
        void navigateBack();
    }

    @Inject
    TimetablePresenter timetablePresenter;
    @Inject ArrivalAdapter arrivalAdapter;

    @BindView(R.id.tv_bollard_name) TextView tv_bollard_name;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rv_timetable) RecyclerView rv_timetable;
    @BindView(R.id.fab_back) FloatingActionButton fab_back;
    @BindView(R.id.fab_refresh) FloatingActionButton fab_refresh;

    private BackClickedListener backClickedListener;

    public TimetableFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BackClickedListener) {
            this.backClickedListener = (BackClickedListener) context;
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
        final View fragmentView = inflater.inflate(R.layout.fragment_timetable_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.timetablePresenter.setView(this);
        this.loadTimetableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.timetablePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.timetablePresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.rv_timetable.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.timetablePresenter.destroy();
    }

    @OnClick(R.id.fab_refresh)
    public void onRefreshClick() {
        this.timetablePresenter.onRefreshButtonClicked();
    }

    @OnClick(R.id.fab_back)
    public void onBackClick() {
        this.timetablePresenter.onBackButtonClicked();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void navigateBack() {
        this.backClickedListener.navigateBack();
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void renderTimetableList(TimetableModel timetables) {
        if (timetables != null) {
            this.tv_bollard_name.setText(timetables.getBollardInfo().getName());
            this.arrivalAdapter.setTimetableCollection(timetables);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getBollardSymbol() {
        return getActivity().getIntent().getExtras().getString(Constants.BOLLARD_SYMBOL);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.rv_timetable.setLayoutManager(new CustomLayoutManager(context()));
        this.rv_timetable.setAdapter(arrivalAdapter);
    }

    private void loadTimetableView() {
        this.timetablePresenter.initialize();
    }
}
