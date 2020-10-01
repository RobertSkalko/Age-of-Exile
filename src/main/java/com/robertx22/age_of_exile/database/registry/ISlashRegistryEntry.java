package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;

public interface ISlashRegistryEntry<C> extends IGUID, IWeighted, ITiered, IRarity {

    SlashRegistryType getSlashRegistryType();

    default void registerToSlashRegistry() {
        SlashRegistry.getRegistry(getSlashRegistryType())
            .register(this);
    }

    default void unregisterFromSlashRegistry() {
        SlashRegistry.getRegistry(getSlashRegistryType())
            .unRegister(this);
    }

    default boolean isUnique() {
        return getRarityRank() == IRarity.Unique;
    }

    default void unregisterDueToInvalidity() {
        SlashRegistry.getRegistry(getSlashRegistryType())
            .unRegister(this);
        try {
            throw new Exception("Registry Entry: " + GUID() + " of type: " + this.getSlashRegistryType()
                .name() + " is invalid! Unregistering");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default boolean isFromDatapack() {
        return false;
    }

    @Override
    default int Weight() {
        return getRarity().Weight();
    }

    default boolean isRegistryEntryValid() {
        // override with an implementation of a validity test
        return true;
    }

    @Override
    default int getRarityRank() {
        return 0;
    }

    @Override
    default Rarity getRarity() {
        return SlashRegistry.GearRarities()
            .get(getRarityRank());
    }

    @Override
    default int getTier() {
        return 0;
    }

    default String getInvalidGuidMessage() {
        return "Non [a-z0-9_.-] character in Mine and Slash GUID: " + GUID() + " of type " + getSlashRegistryType().name();
    }

}
