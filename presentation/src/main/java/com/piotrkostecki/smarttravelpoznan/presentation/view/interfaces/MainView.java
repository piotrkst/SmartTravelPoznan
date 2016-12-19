package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;

import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;

import java.util.Collection;

public interface MainView extends LoadDataView {

    void showDirections();

    void hideDirections();

    void viewTimetable();

    void renderDirectionList(Collection<DirectionModel> directionCollection);
}
