package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public class RarityRegistryContainer<T extends Rarity> extends ExileRegistryContainer<T> {

    public RarityRegistryContainer(ExileRegistryTypes type, T emptyDefault) {
        super(type, emptyDefault);
    }

}
