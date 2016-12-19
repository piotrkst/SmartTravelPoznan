package com.piotrkostecki.smarttravelpoznan.domain.repository;

import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting PEKA related data.
 */
public interface PekaRepository {

    Observable<List<Direction>> directions(String stopName);

    Observable<List<Timetable>> timetables();
}
