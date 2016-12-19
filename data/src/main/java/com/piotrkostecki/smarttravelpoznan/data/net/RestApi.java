package com.piotrkostecki.smarttravelpoznan.data.net;

import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL = "http://www.android10.org/myapi/";

    /** Api url for getting all users */
    String API_URL_GET_DIRECTION_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_TIMETABLES_DETAILS = API_BASE_URL + "user_";

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link DirectionEntity}.
     * @param stopName used to get direction data.
     */
    Observable<List<DirectionEntity>> directionEntityList(String stopName);

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link TimetableEntity}.
     *
     */
    Observable<List<TimetableEntity>> timetableEntityList();
}
