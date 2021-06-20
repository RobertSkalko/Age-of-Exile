package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.library_of_exile.registry.IGUID;

public interface ExileRegistry<C> extends IGUID, IWeighted {

    ExileRegistryTypes getExileRegistryType();

    default void registerToExileRegistry() {
        Database.getRegistry(getExileRegistryType())
            .register(this);
    }

    default void unregisterFromExileRegistry() {
        Database.getRegistry(getExileRegistryType())
            .unRegister(this);
    }

    default void unregisterDueToInvalidity() {
        Database.getRegistry(getExileRegistryType())
            .unRegister(this);
        try {
            throw new Exception("Registry Entry: " + GUID() + " of type: " + this.getExileRegistryType()
                .name() + " is invalid! Unregistering");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default boolean isFromDatapack() {
        return false;
    }

    default boolean isRegistryEntryValid() {
        // override with an implementation of a validity test
        return true;
    }

    default String getInvalidGuidMessage() {
        return "Non [a-z0-9_.-] character in Age of Exile GUID: " + GUID() + " of type " + getExileRegistryType().name();
    }

}
