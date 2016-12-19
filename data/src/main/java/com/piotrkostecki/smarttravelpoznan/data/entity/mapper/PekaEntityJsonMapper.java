package com.piotrkostecki.smarttravelpoznan.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;
import com.piotrkostecki.smarttravelpoznan.data.entity.TimetableEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class PekaEntityJsonMapper {

    private final Gson gson;

    @Inject
    public PekaEntityJsonMapper() {
        this.gson = new Gson();
    }

    public List<DirectionEntity> transformDirectionEntityCollection(String directionListJsonResponse) throws JsonSyntaxException {
        List<DirectionEntity> directionEntityCollection;
        try {
            Type listOfDirectionEntityType = new TypeToken<List<DirectionEntity>>() {}.getType();
            directionEntityCollection = this.gson.fromJson(directionListJsonResponse, listOfDirectionEntityType);

            return directionEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    public List<TimetableEntity> transformTimetableEntityCollection(String timetableListJsonResponse) throws JsonSyntaxException {
        List<TimetableEntity> timetableEntityCollection;
        try {
            Type listOfTimetableEntityType = new TypeToken<List<TimetableEntity>>() {}.getType();
            timetableEntityCollection = this.gson.fromJson(timetableListJsonResponse, listOfTimetableEntityType);

            return timetableEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
