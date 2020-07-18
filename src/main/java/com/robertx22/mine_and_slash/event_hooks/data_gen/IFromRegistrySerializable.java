package com.robertx22.mine_and_slash.event_hooks.data_gen;

import com.google.gson.JsonObject;

public interface IFromRegistrySerializable<T> {

    JsonObject toRegistryJson();

    T fromRegistryJson(JsonObject json);

}
