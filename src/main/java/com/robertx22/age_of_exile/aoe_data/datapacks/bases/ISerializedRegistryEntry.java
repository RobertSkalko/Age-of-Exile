package com.robertx22.age_of_exile.aoe_data.datapacks.bases;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;

public interface ISerializedRegistryEntry<T> extends ISlashRegistryEntry<T> {

    default void addToSerializables() {
        Database.getRegistry(getSlashRegistryType())
            .addSerializable(this);
    }

    default Object addToSerReturn() {
        addToSerializables();
        return this;
    }

    @Override
    default boolean isFromDatapack() {
        return true;
    }

}
