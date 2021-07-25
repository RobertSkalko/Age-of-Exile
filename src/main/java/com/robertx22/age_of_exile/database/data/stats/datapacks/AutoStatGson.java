package com.robertx22.age_of_exile.database.data.stats.datapacks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public interface AutoStatGson<T extends AutoStatGson<T>> {
    Gson GSON = new Gson();
    JsonParser PARSER = new JsonParser();

    default JsonObject toJson() {
        return PARSER.parse(GSON.toJson(this))
            .getAsJsonObject();
    }

    default String toJsonString() {
        return GSON.toJson(this);
    }

    default void onLoadedFromJson() {
    }

    default T fromJson(JsonObject json) {
        T t = (T) GSON.fromJson(json, this.getClassForSerialization());
        t.onLoadedFromJson();
        return t;
    }

    Class<T> getClassForSerialization();
}