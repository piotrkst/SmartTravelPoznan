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

    /** Api url for getting all users */
    String API_URL_GET_DIRECTION_LIST = API_BASE_URL;
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_TIMETABLES_DETAILS = API_BASE_URL;

    Observable<List<StopEntity>> stopEntityList(String stopName);

    Observable<TimetableEntity> timetableEntityList(String bollardSymbol);

    Observable<List<BollardEntity>> bollardEntityList(String stopName);
}
