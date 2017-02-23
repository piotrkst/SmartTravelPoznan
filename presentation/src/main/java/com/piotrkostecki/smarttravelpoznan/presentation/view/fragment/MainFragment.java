package com.piotrkostecki.smarttravelpoznan.presentation.view.fragment;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class MainFragment extends BaseFragment implements MainView,
        DialogBollards.BollardListListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    public interface DirectionSelectedListener {
        void navigateToTimetable(String bollard);
    }

    @Inject MainPresenter mainPresenter;
    @Inject StopAdapter stopAdapter;

    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_progress_stops) RelativeLayout rl_progress_stops;
    @BindView(R.id.et_stop_name) EditText et_stopName;
    @BindView(R.id.tv_prompt) TextView tv_prompt;
    @BindView(R.id.rl_root) RelativeLayout rl_root;
    @BindView(R.id.rl_container) RelativeLayout rl_container;
    @BindView(R.id.fab_search) FloatingActionButton btn_search;
    @BindView(R.id.rv_stop_names) RecyclerView rv_directions;
    @BindView(R.id.view_dummy) View view_dummy;
    @BindView(R.id.rl_cancel) RelativeLayout rl_cancel;

    private boolean viewCentered = true;
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
        this.mainPresenter.initialize();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainPresenter.setView(this);
        this.rl_root.getViewTreeObserver().addOnGlobalLayoutListener(this);
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
        this.rl_root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mainPresenter.destroy();
    }

    @OnClick(R.id.rl_cancel)
    public void deleteText() {
        this.mainPresenter.clearText();
    }

    @OnClick(R.id.fab_search)
    public void getBollardList() {
        this.mainPresenter.onSearchClick(et_stopName.getText().toString());
    }

    @OnTextChanged(R.id.et_stop_name)
    public void getStopNameList() {
        this.mainPresenter.onStopNameChanged(et_stopName.getText().toString());
    }

    @Override
    public void eraseText() {
        this.et_stopName.setText("");
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
    public void fillTextWithStopName(String direction) {
        this.et_stopName.setText(direction);
    }

    @Override
    public void showStopListLoading() {
        this.rl_progress_stops.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideStopListLoading() {
        this.rl_progress_stops.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
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
    public void showError(String message) {
        showToastMessage(message);
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
    public void onGlobalLayout() {
        this.mainPresenter.layoutChanged();
    }

    @Override
    public void handleLayoutChange() {
        int navigationBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int statusBarHeight = 0;
        resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        int keyboardHeight = rl_root.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + rect.height());

        if (keyboardHeight <= 0) {
            if (!viewCentered) {
                this.mainPresenter.centerLayout();
            }
        } else {
            if (viewCentered) {
                this.mainPresenter.scrollLayout();
            }
        }
    }

    @Override
    public void hideEraseButton() {
        this.rl_cancel.setVisibility(View.GONE);
    }

    @Override
    public void showEraseButton() {
        this.rl_cancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void centerLayout() {
        viewCentered = true;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rl_container.getLayoutParams();
        params.topMargin = 0;
        params.bottomMargin = 50;
        params.gravity = Gravity.CENTER_VERTICAL;
        rl_container.setLayoutParams(params);
    }

    @Override
    public void scrollLayout() {
        viewCentered = false;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rl_container.getLayoutParams();
        params.topMargin = 50;
        params.bottomMargin = 0;
        params.gravity = Gravity.TOP;
        rl_container.setLayoutParams(params);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
