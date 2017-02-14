package com.piotrkostecki.smarttravelpoznan.presentation.mapper;

import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.model.StopModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Stop} (in the domain layer) to {@link StopModel} in the
 * presentation layer.
 */
@PerActivity
public class StopModelDataMapper {

    @Inject
    public StopModelDataMapper() {}

    /**
     * Transform a {@link Stop} into an {@link StopModel}.
     *
     * @param stop Object to be transformed.
     * @return {@link StopModel}.
     */
    public StopModel transform(Stop stop) {
        if (stop == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        StopModel stopModel = new StopModel(stop.getSymbol(), stop.getName());

        return stopModel;
    }

    /**
     * Transform a Collection of {@link Stop} into a Collection of {@link StopModel}.
     *
     * @param stopCollection Objects to be transformed.
     * @return List of {@link StopModel}.
     */
    public Collection<StopModel> transform(Collection<Stop> stopCollection) {
        Collection<StopModel> stopModelCollection;

        if (stopCollection != null && !stopCollection.isEmpty()) {
            stopModelCollection = new ArrayList<>();
            for (Stop stop : stopCollection) {
                stopModelCollection.add(transform(stop));
            }
        } else {
            stopModelCollection = Collections.emptyList();
        }

        return stopModelCollection;
    }
}
