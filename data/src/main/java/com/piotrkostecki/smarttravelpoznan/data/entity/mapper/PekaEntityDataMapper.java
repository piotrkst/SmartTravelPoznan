package com.piotrkostecki.smarttravelpoznan.data.entity.mapper;

import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform PekaEntities (in the data layer) to Peka in the
 * domain layer.
 */
@Singleton
public class PekaEntityDataMapper {

    @Inject
    public PekaEntityDataMapper() {}

    public Direction transform(DirectionEntity directionEntity) {
        Direction direction = null;
        if (directionEntity != null) {
            direction = new Direction();
            direction.setDirectionContent(directionEntity.getDirectionContent());
        }

        return direction;
    }

    public List<Direction> transformDirection(Collection<DirectionEntity> directionEntityCollection) {
        List<Direction> directionList = new ArrayList<>(10);
        Direction direction;
        for (DirectionEntity directionEntity : directionEntityCollection) {
            direction = transform(directionEntity);
            if (direction != null) {
                directionList.add(direction);
            }
        }

        return directionList;
    }

    public Timetable transform(TimetableEntity timetableEntity) {
        Timetable timetable = null;
        if (timetableEntity != null) {
            timetable = new Timetable();
        }

        return timetable;
    }

    public List<Timetable> transformTimetable(Collection<TimetableEntity> timetableEntityCollection) {
        List<Timetable> timetableList = new ArrayList<>(20);
        Timetable timetable;
        for (TimetableEntity timetableEntity : timetableEntityCollection) {
            timetable = transform(timetableEntity);
            if (timetable != null) {
                timetableList.add(timetable);
            }
        }

        return timetableList;
    }

}
