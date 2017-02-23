package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;

import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;

import java.util.Collection;

public interface MainView extends LoadDataView {

    void showStopListLoading();

    void hideStopListLoading();

    void renderStopList(Collection<StopModel> directionCollection);

    void renderBollardList(Collection<BollardModel> bollardCollection);

    void changePromptToHints();

    void changePromptToRecentSearches();

    void navigateToTimetable(BollardModel bollardModel);

    void showEraseButton();

    void fillTextWithStopName(String direction);

    void eraseText();

    void hideEraseButton();

    void handleLayoutChange();

    void scrollLayout();

    void centerLayout();
}
