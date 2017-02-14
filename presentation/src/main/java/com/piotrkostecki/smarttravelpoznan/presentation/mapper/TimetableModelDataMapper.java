package com.piotrkostecki.smarttravelpoznan.presentation.mapper;

import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.model.TimetableModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;


@PerActivity
public class TimetableModelDataMapper {

    @Inject
    public TimetableModelDataMapper() {}

    /**
     * Transform a {@link Timetable} into an {@link TimetableModel}.
     *
     * @param timetable Object to be transformed.
     * @return {@link TimetableModel}.
     */
    public TimetableModel transform(Timetable timetable) {
        if (timetable == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        TimetableModel timetableModel = new TimetableModel(timetable.getBollardInfo(), timetable.getArrivals());

        return timetableModel;
    }

    /**
     * Transform a Collection of {@link Timetable} into a Collection of {@link TimetableModel}.
     *
     * @param timetableCollection Objects to be transformed.
     * @return List of {@link TimetableModel}.
     */
    public Collection<TimetableModel> transform(Collection<Timetable> timetableCollection) {
        Collection<TimetableModel> timetableModelCollection;

        if (timetableCollection != null && !timetableCollection.isEmpty()) {
            timetableModelCollection = new ArrayList<>();
            for (Timetable timetable : timetableCollection) {
                timetableModelCollection.add(transform(timetable));
            }
        } else {
            timetableModelCollection = Collections.emptyList();
        }

        return timetableModelCollection;
    }
}
