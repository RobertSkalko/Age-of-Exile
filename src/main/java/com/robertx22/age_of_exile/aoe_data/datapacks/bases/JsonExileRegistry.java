package com.robertx22.age_of_exile.aoe_data.datapacks.bases;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistry;

public interface JsonExileRegistry<T> extends ExileRegistry<T> {

    default void addToSerializables() {
        Database.getRegistry(getExileRegistryType())
            .addSerializable(this);
    }

    @Override
    default boolean isFromDatapack() {
        return true;
    }

}
