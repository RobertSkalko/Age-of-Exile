package com.robertx22.age_of_exile.datapacks.bases;

import com.google.gson.JsonObject;

public interface IFromRegistrySerializable<T> {

    JsonObject toRegistryJson();

    T fromRegistryJson(JsonObject json);

}
