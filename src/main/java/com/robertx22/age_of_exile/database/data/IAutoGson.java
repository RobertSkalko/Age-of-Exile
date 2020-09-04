package com.robertx22.age_of_exile.database.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;

public interface IAutoGson<T extends IAutoGson<T>> extends ISerializable<T> {

    Gson GSON = new Gson();

    @Override
    default JsonObject toJson() {
        return new JsonParser().parse(GSON.toJson(this))
            .getAsJsonObject();
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
