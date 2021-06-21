package com.robertx22.age_of_exile.database.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;

public interface IAutoGson<T extends IAutoGson<T>> extends ISerializable<T> {

    Gson GSON = new Gson();

    JsonParser PARSER = new JsonParser();

    @Override
    default JsonObject toJson() {
        return PARSER.parse(GSON.toJson(this))
            .getAsJsonObject();
    }

    @Override
    default String toJsonString() {
        return GSON.toJson(this);
    }

    default void onLoadedFromJson() {

    }

    @Override
    default T fromJson(JsonObject json) {
        T t = GSON.fromJson(json, getClassForSerialization());
        t.onLoadedFromJson();
        return t;
    }

    Class<T> getClassForSerialization();

}
