package com.piotrkostecki.smarttravelpoznan.data.cache.serializer;

import com.google.gson.Gson;
import com.piotrkostecki.smarttravelpoznan.data.entity.DirectionEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class user as Serializer/Deserializer for user entities.
 */
@Singleton
public class JsonSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JsonSerializer() {}

    /**
     * Serialize an object to Json.
     *
     * @param directionEntity {@link DirectionEntity} to serialize.
     */
    public String serialize(DirectionEntity directionEntity) {
        String jsonString = gson.toJson(directionEntity, DirectionEntity.class);
        return jsonString;
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param jsonString A json string to deserialize.
     * @return {@link DirectionEntity}
     */
    public DirectionEntity deserialize(String jsonString) {
        DirectionEntity directionEntity = gson.fromJson(jsonString, DirectionEntity.class);
        return directionEntity;
    }
}
