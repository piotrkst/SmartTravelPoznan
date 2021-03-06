package com.piotrkostecki.smarttravelpoznan.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.piotrkostecki.smarttravelpoznan.data.entity.BollardEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transformTimetable from Strings representing json to valid objects.
 */
public class PekaEntityJsonMapper {

    private final Gson gson;

    @Inject
    public PekaEntityJsonMapper() {
        this.gson = new Gson();
    }

    public List<StopEntity> transformStopEntityCollection(String stopListJsonResponse) throws JsonSyntaxException {
        JsonParser parser = new JsonParser();
        List<StopEntity> stopEntityCollection;
        JsonArray stopListJsonArrayResponse = parser.parse(stopListJsonResponse).getAsJsonObject().get("success").getAsJsonArray();
        try {
            Type listOfStopEntityType = new TypeToken<List<StopEntity>>() {}.getType();
            stopEntityCollection = this.gson.fromJson(stopListJsonArrayResponse, listOfStopEntityType);

            return stopEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            jsonException.printStackTrace();
            throw jsonException;
        }

    }

    public List<BollardEntity> transformBollardEntityCollection(String bollardListJsonResponse) throws JsonSyntaxException {
        JsonParser parser = new JsonParser();
        List<BollardEntity> bollardEntityCollection;
        JsonArray bollardListJsonArrayResponse = parser.parse(bollardListJsonResponse)
                .getAsJsonObject().get("success")
                .getAsJsonObject().get("bollards")
                .getAsJsonArray();
        try {
            Type listOfBollardEntityType = new TypeToken<List<BollardEntity>>() {}.getType();
            bollardEntityCollection = this.gson.fromJson(bollardListJsonArrayResponse, listOfBollardEntityType);

            return bollardEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            jsonException.printStackTrace();
            throw jsonException;
        }
    }

    public TimetableEntity transformTimetableEntity(String timetableJsonResponse) throws JsonSyntaxException {
        JsonParser parser = new JsonParser();
        TimetableEntity timetableEntity;
        JsonObject timetableJsonObjectResponse = parser.parse(timetableJsonResponse)
                .getAsJsonObject().get("success")
                .getAsJsonObject();
        try {
            Type timetableEntityType = new TypeToken<TimetableEntity>() {}.getType();
            timetableEntity = this.gson.fromJson(timetableJsonObjectResponse, timetableEntityType);

            return timetableEntity;
        } catch (JsonSyntaxException jsonException) {
            jsonException.printStackTrace();
            throw jsonException;
        }
    }
}
