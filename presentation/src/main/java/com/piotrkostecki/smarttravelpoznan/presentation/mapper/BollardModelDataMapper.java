package com.piotrkostecki.smarttravelpoznan.presentation.mapper;


import com.piotrkostecki.smarttravelpoznan.domain.model.Bollard;
import com.piotrkostecki.smarttravelpoznan.presentation.model.BollardModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

public class BollardModelDataMapper {

    @Inject
    public BollardModelDataMapper() {}

    /**
     * Transform a {@link Bollard} into an {@link BollardModel}.
     *
     * @param bollard Object to be transformed.
     * @return {@link BollardModel}.
     */
    public BollardModel transform(Bollard bollard) {
        if (bollard == null) {
            throw new IllegalArgumentException("Cannot transformTimetable a null value");
        }
        BollardModel bollardModel = new BollardModel(bollard.getDirections(), bollard.getBollardInfo());

        return bollardModel;
    }

    /**
     * Transform a Collection of {@link Bollard} into a Collection of {@link BollardModel}.
     *
     * @param bollardCollection Objects to be transformed.
     * @return List of {@link BollardModel}.
     */
    public Collection<BollardModel> transform(Collection<Bollard> bollardCollection) {
        Collection<BollardModel> bollardModelCollection;

        if (bollardCollection != null && !bollardCollection.isEmpty()) {
            bollardModelCollection = new ArrayList<>();
            for (Bollard bollard : bollardCollection) {
                bollardModelCollection.add(transform(bollard));
            }
        } else {
            bollardModelCollection = Collections.emptyList();
        }

        return bollardModelCollection;
    }
}
