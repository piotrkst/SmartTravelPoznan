package com.piotrkostecki.smarttravelpoznan.presentation.view.interfaces;


import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link StopModel}.
 */
public interface TimetableListView extends LoadDataView {

    void navigateBack();

    void renderTimetableList(TimetableModel timetables);

    String getBollardSymbol();
}
