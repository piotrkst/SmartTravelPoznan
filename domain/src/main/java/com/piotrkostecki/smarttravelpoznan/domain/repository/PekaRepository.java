package com.piotrkostecki.smarttravelpoznan.domain.repository;

import com.piotrkostecki.smarttravelpoznan.domain.model.Bollard;
import com.piotrkostecki.smarttravelpoznan.domain.model.Stop;
import com.piotrkostecki.smarttravelpoznan.domain.model.Timetable;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting PEKA related data.
 */
public interface PekaRepository {

    Observable<List<Stop>> searches();

    Observable<List<Stop>> stops(String stopName);

    Observable<List<Bollard>> bollards(String stopName);

    Observable<Timetable> timetables(String bollardSymbol);
}
