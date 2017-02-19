package com.piotrkostecki.smarttravelpoznan.data.entity.mapper;

import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.domain.model.Bollard;
import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transformTimetable PekaEntities (in the data layer) to Peka in the
 * domain layer.
 */
@Singleton
public class PekaEntityDataMapper {

    @Inject
    public PekaEntityDataMapper() {}

    public Bollard transformTimetable(BollardEntity bollardEntity) {
        Bollard bollard = null;
        if (bollardEntity != null) {
            bollard = new Bollard(bollardEntity.getDirections(), bollardEntity.getBollardInfo());
        }

        return bollard;
    }

    public List<Bollard> transformBollard(Collection<BollardEntity> bollardEntityCollection) {
        List<Bollard> bollardList = new ArrayList<>(10);
        Bollard bollard;
        for (BollardEntity bollardEntity : bollardEntityCollection) {
            bollard = transformTimetable(bollardEntity);
            if (bollard != null) {
                bollardList.add(bollard);
            }
        }

        return bollardList;
    }

    public Stop transformTimetable(StopEntity stopEntity) {
        Stop stop = null;
        if (stopEntity != null) {
            stop = new Stop(stopEntity.getSymbol(), stopEntity.getName());
        }

        return stop;
    }

    public List<Stop> transformStop(Collection<StopEntity> stopEntityCollection) {
        List<Stop> stopList = new ArrayList<>(10);
        Stop stop;
        for (StopEntity stopEntity : stopEntityCollection) {
            stop = transformTimetable(stopEntity);
            if (stop != null) {
                stopList.add(stop);
            }
        }

        return stopList;
    }

    public Timetable transformTimetable(TimetableEntity timetableEntity) {
        Timetable timetable = null;
        if (timetableEntity != null) {
            timetable = new Timetable(timetableEntity.getBollardInfo(), timetableEntity.getArrivals());
        }

        return timetable;
    }

    public List<Timetable> transformTimetable(Collection<TimetableEntity> timetableEntityCollection) {
        List<Timetable> timetableList = new ArrayList<>(20);
        Timetable timetable;
        for (TimetableEntity timetableEntity : timetableEntityCollection) {
            timetable = transformTimetable(timetableEntity);
            if (timetable != null) {
                timetableList.add(timetable);
            }
        }

        return timetableList;
    }
}
