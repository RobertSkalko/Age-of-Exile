package com.robertx22.age_of_exile.aoe_data.datapacks.bases;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public interface ISerializedRegistryEntry<T> extends ISlashRegistryEntry<T> {

    default void addToSerializables() {
        SlashRegistry.getRegistry(getSlashRegistryType())
            .addSerializable(this);

    }

    @Override
    default boolean isFromDatapack() {
        return true;
    }

}
