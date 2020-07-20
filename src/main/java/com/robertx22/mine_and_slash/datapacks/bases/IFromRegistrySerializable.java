package com.robertx22.mine_and_slash.datapacks.bases;

import com.google.gson.JsonObject;

public interface IFromRegistrySerializable<T> {

    JsonObject toRegistryJson();

    T fromRegistryJson(JsonObject json);

}
