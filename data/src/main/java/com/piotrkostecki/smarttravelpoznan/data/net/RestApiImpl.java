package com.piotrkostecki.smarttravelpoznan.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.mapper.PekaEntityJsonMapper;
import com.piotrkostecki.smarttravelpoznan.data.exception.NetworkConnectionException;

import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final PekaEntityJsonMapper pekaEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context {@link android.content.Context}.
     * @param pekaEntityJsonMapper {@link PekaEntityJsonMapper}.
     */
    public RestApiImpl(Context context, PekaEntityJsonMapper pekaEntityJsonMapper) {
        if (context == null || pekaEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.pekaEntityJsonMapper = pekaEntityJsonMapper;
    }

    @RxLogObservable
    @Override public Observable<List<DirectionEntity>> directionEntityList(String stopName) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseDirectionEntities = getDirectionEntitiesFromApi(stopName);
                    if (responseDirectionEntities != null) {
                        subscriber.onNext(pekaEntityJsonMapper.transformDirectionEntityCollection(responseDirectionEntities));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    @RxLogObservable
    @Override public Observable<List<TimetableEntity>> timetableEntityList() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseTimetableEntities = getTimetableEntitiesFromApi();
                    if (responseTimetableEntities != null) {
                        subscriber.onNext(pekaEntityJsonMapper.transformTimetableEntityCollection(responseTimetableEntities));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    private String getDirectionEntitiesFromApi(String stopName) throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_DIRECTION_LIST).requestSyncCall();
    }

    private String getTimetableEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_TIMETABLES_DETAILS).requestSyncCall();
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
