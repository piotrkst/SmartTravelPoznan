package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;


import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link StopModel}.
 */
public interface TimetableListView extends LoadDataView {

    /**
     * Render a user list in the UI.
     *
     * @param timetables The collection of {@link StopModel} that will be shown.
     */
    void renderTimetableList(TimetableModel timetables);

    /**
     * View a {@link StopModel} profile/details.
     *
     * @param timetableModel The user that will be shown.
     */
    void viewTimetable(TimetableModel timetableModel);

    String getBollardSymbol();
}
