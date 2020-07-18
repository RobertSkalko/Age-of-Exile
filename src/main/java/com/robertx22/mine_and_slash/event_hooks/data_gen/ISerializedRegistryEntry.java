package com.robertx22.mine_and_slash.event_hooks.data_gen;

import com.google.gson.JsonObject;
import com.robertx22.exiled_lib.registry.ISlashRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;

public interface ISerializedRegistryEntry<T> extends ISlashRegistryEntry<T>, IFromRegistrySerializable<T> {

    default void addToSerializables() {
        SlashRegistry.getRegistry(getSlashRegistryType())
            .addSerializable(this);
    }

    @Override
    default JsonObject toRegistryJson() {
        JsonObject json = new JsonObject();
        json.addProperty(ISerializable.ID, GUID());
        json.addProperty(ISerializable.REGISTRY, this.getSlashRegistryType().id);
        return json;

    }

    @Override
    default boolean isFromDatapack() {
        return true;
    }

    @Override
    default T fromRegistryJson(JsonObject json) {
        SlashRegistryType type = SlashRegistryType.getFromString(json.get(ISerializable.REGISTRY)
            .getAsString());
        String id = json.get(ISerializable.ID)
            .getAsString();
        return (T) SlashRegistry.get(type, id);

    }

}
