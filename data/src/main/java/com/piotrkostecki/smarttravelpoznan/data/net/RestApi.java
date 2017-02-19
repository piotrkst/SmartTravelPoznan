package com.piotrkostecki.smarttravelpoznan.data.net;

import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL = "https://www.peka.poznan.pl/vm/method.vm?ts=";

    Observable<List<StopEntity>> stopEntityList(String stopName);

    Observable<TimetableEntity> timetableEntityList(String bollardSymbol);

    Observable<List<BollardEntity>> bollardEntityList(String stopName);
}
