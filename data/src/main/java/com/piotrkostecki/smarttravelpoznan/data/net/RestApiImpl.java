package com.piotrkostecki.smarttravelpoznan.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.mapper.PekaEntityJsonMapper;
import com.piotrkostecki.smarttravelpoznan.data.exception.NetworkConnectionException;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final PekaEntityJsonMapper pekaEntityJsonMapper;

    private static final String METHOD_GET_STOP_POINTS = "getStopPoints";
    private static final String METHOD_GET_BOLLARDS = "getBollardsByStopPoint";
    private static final String METHOD_GET_TIMES = "getTimes";

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
    @Override public Observable<List<StopEntity>> stopEntityList(String stopName) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseStopEntities = getStopEntitiesFromApi(stopName);
                    if (responseStopEntities != null) {
                        subscriber.onNext(pekaEntityJsonMapper.transformStopEntityCollection(responseStopEntities));
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
    @Override
    public Observable<List<BollardEntity>> bollardEntityList(String stopName) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseBollardEntities = getBollardEntitiesFromApi(stopName);
                    if (responseBollardEntities != null) {
                        subscriber.onNext(pekaEntityJsonMapper.transformBollardEntityCollection(responseBollardEntities));
                        subscriber.onCompleted();
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
    @Override public Observable<TimetableEntity> timetableEntityList(String bollardSymbol) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseTimetableEntities = getTimetableEntitiesFromApi(bollardSymbol);
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

    private String getStopEntitiesFromApi(String stopName) throws MalformedURLException, UnsupportedEncodingException {
        String apiUrl = API_BASE_URL + getTimestamp();
        return ApiConnection.createGET(apiUrl).requestSyncCall(METHOD_GET_STOP_POINTS, "{\"pattern\":\"" + stopName + "\"}");
    }

    private String getBollardEntitiesFromApi(String stopName) throws MalformedURLException, UnsupportedEncodingException {
        String apiUrl = API_BASE_URL + getTimestamp();
        return ApiConnection.createGET(apiUrl).requestSyncCall(METHOD_GET_BOLLARDS, "{\"name\":\"" + stopName + "\"}");
    }

    private String getTimetableEntitiesFromApi(String bollardSymbol) throws MalformedURLException, UnsupportedEncodingException {
        return ApiConnection.createGET(API_URL_GET_TIMETABLES_DETAILS).requestSyncCall(METHOD_GET_TIMES, "{\"symbol\":\"" + bollardSymbol + "\"}");
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    private String getTimestamp() {
        Long tsLong = System.currentTimeMillis();
        return tsLong.toString();
    }

    private String decodeContent(String method, String p0) throws UnsupportedEncodingException {
        String urlParameters = "method=" + URLEncoder.encode(method,"UTF-8") + "&p0=" + URLEncoder.encode(p0, "UTF-8");

        return urlParameters;
    }
}
