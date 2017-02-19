package com.piotrkostecki.smarttravelpoznan.presentation.mapper;

import com.piotrkostecki.smarttravelpoznan.domain.model.Arrival;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;
import com.piotrkostecki.smarttravelpoznan.presentation.R;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.model.ArrivalModel;
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
        TimetableModel timetableModel = new TimetableModel();
        timetableModel.setBollardInfo(timetable.getBollardInfo());
        timetableModel.setArrivals(transform(timetable.getArrivals()));

        return timetableModel;
    }

    public ArrivalModel transform(Arrival arrival) {
        if (arrival == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ArrivalModel arrivalModel = new ArrivalModel();
        arrivalModel.setDeparture(arrival.getDeparture());
        arrivalModel.setDirection(arrival.getDirection());
        arrivalModel.setLine(arrival.getLine());
        arrivalModel.setMinutes(arrival.getMinutes());
        arrivalModel.setOnStopPoint(arrival.isOnStopPoint());
        arrivalModel.setRealTime(arrival.isRealTime());

        return arrivalModel;
    }

    public Collection<ArrivalModel> transform(Collection<Arrival> arrivalCollection) {
        Collection<ArrivalModel> arrivalModelCollection;

        if (arrivalCollection != null && !arrivalCollection.isEmpty()) {
            arrivalModelCollection = new ArrayList<>();
            for (Arrival arrival : arrivalCollection) {
                arrivalModelCollection.add(transform(arrival));
            }
        } else {
            arrivalModelCollection = Collections.emptyList();
        }

        return arrivalModelCollection;
    }
}
