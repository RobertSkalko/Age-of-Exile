package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public class RarityRegistryContainer<T extends Rarity> extends ExileRegistryContainer<T> {

    public RarityRegistryContainer(ExileRegistryType type, T emptyDefault) {
        super(type, emptyDefault);
    }

}
