package com.piotrkostecki.smarttravelpoznan.presentation.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.components.PekaComponent;
import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.presenter.MainPresenter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.StopAdapter;
import com.piotrkostecki.smarttravelpoznan.presentation.view.adapter.CustomLayoutManager;
import com.piotrkostecki.smarttravelpoznan.presentation.view.component.DialogBollards;
import com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces.MainView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainFragment extends BaseFragment implements MainView,
        DialogBollards.BollardListListener {

    public interface DirectionSelectedListener {
        void navigateToTimetable(String bollard);
    }

    @Inject MainPresenter mainPresenter;
    @Inject StopAdapter stopAdapter;

    @BindView(R.id.et_stop_name) EditText et_stopName;
    @BindView(R.id.tv_prompt) TextView tv_prompt;
    @BindView(R.id.btn_search) Button btn_search;
    @BindView(R.id.rv_stop_names) RecyclerView rv_directions;

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
        setupRecyclerView();
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
        this.rv_directions.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mainPresenter.destroy();
    }

    @OnClick(R.id.btn_search)
    public void getBollardList() {
        mainPresenter.onSearchClick(et_stopName.getText().toString());
    }

    @OnTextChanged(R.id.et_stop_name)
    public void getStopNameList() {
        mainPresenter.onStopNameChanged(et_stopName.getText().toString());
    }

    @Override
    public void changePromptToHints() {
        this.tv_prompt.setText(getString(R.string.tv_prompt_hints));
    }

    @Override
    public void changePromptToRecentSearches() {
        this.tv_prompt.setText(getString(R.string.tv_prompt_recent_searches));
    }

    @Override
    public void showStops() {

    }

    @Override
    public void hideStops() {

    }


    @Override
    public void renderStopList(Collection<StopModel> directionCollection) {
        if (directionCollection != null) {
            this.stopAdapter.setStopCollection(directionCollection);
        }
    }

    @Override
    public void renderBollardList(Collection<BollardModel> bollardCollection) {
        if (bollardCollection != null) {
            DialogBollards dialogBollards = new DialogBollards();
            dialogBollards.setOnBollardClickListener(this);
            dialogBollards.setBollardCollection(bollardCollection);
            dialogBollards.show(getFragmentManager(), "test");
        }
    }

    @Override
    public void fillEditTextWithStopName(String direction) {
        this.et_stopName.setText(direction);
    }

    @Override
    public void showStopListLoading() {

    }

    @Override
    public void hideStopListLoading() {

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
    public void showError(String message) {

    }

    @Override
    public void onBollardClicked(BollardModel bollardModel) {
        this.mainPresenter.onBollardClicked(bollardModel);
    }

    @Override
    public void navigateToTimetable(BollardModel bollardModel) {
        this.directionSelectedListener.navigateToTimetable(bollardModel.getBollardInfo().getSymbol());
    }

    private void setupRecyclerView() {
        this.stopAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_directions.setLayoutManager(new CustomLayoutManager(context()));
        this.rv_directions.setAdapter(stopAdapter);
    }

    private StopAdapter.OnItemClickListener onItemClickListener =
            stopModel -> {
                if (MainFragment.this.mainPresenter != null && stopModel != null) {
                    MainFragment.this.mainPresenter.onDirectionClick(stopModel);
                }
            };

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
