package com.piotrkostecki.smarttravelpoznan.data.cache.serializer;

import com.google.gson.Gson;
import com.piotrkostecki.smarttravelpoznan.data.entity.StopEntity;

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
     * @param stopEntity {@link StopEntity} to serialize.
     */
    public String serialize(StopEntity stopEntity) {
        String jsonString = gson.toJson(stopEntity, StopEntity.class);
        return jsonString;
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param jsonString A json string to deserialize.
     * @return {@link StopEntity}
     */
    public StopEntity deserialize(String jsonString) {
        StopEntity stopEntity = gson.fromJson(jsonString, StopEntity.class);
        return stopEntity;
    }
}
