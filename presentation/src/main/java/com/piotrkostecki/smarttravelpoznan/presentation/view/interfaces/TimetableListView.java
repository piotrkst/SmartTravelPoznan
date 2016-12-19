package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;


import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link DirectionModel}.
 */
public interface TimetableListView extends LoadDataView {

    /**
     * Render a user list in the UI.
     *
     * @param directionModelCollection The collection of {@link DirectionModel} that will be shown.
     */
    void renderTimetableList(Collection<DirectionModel> directionModelCollection);

    /**
     * View a {@link DirectionModel} profile/details.
     *
     * @param directionModel The user that will be shown.
     */
    void viewTimetable(DirectionModel directionModel);
}
