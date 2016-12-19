package com.piotrkostecki.smarttravelpoznan.presentation.mapper;

import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;
import com.piotrkostecki.smarttravelpoznan.presentation.internal.di.PerActivity;
import com.piotrkostecki.smarttravelpoznan.presentation.model.DirectionModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Direction} (in the domain layer) to {@link DirectionModel} in the
 * presentation layer.
 */
@PerActivity
public class DirectionModelDataMapper {

    @Inject
    public DirectionModelDataMapper() {}

    /**
     * Transform a {@link Direction} into an {@link DirectionModel}.
     *
     * @param direction Object to be transformed.
     * @return {@link DirectionModel}.
     */
    public DirectionModel transform(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        DirectionModel directionModel = new DirectionModel(direction.getDirectionContent());

        return directionModel;
    }

    /**
     * Transform a Collection of {@link Direction} into a Collection of {@link DirectionModel}.
     *
     * @param directionCollection Objects to be transformed.
     * @return List of {@link DirectionModel}.
     */
    public Collection<DirectionModel> transform(Collection<Direction> directionCollection) {
        Collection<DirectionModel> directionModelCollection;

        if (directionCollection != null && !directionCollection.isEmpty()) {
            directionModelCollection = new ArrayList<>();
            for (Direction direction : directionCollection) {
                directionModelCollection.add(transform(direction));
            }
        } else {
            directionModelCollection = Collections.emptyList();
        }

        return directionModelCollection;
    }
}
