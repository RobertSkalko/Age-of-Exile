package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public class RarityRegistryContainer<T extends Rarity> extends SlashRegistryContainer<T> {

    public RarityRegistryContainer(SlashRegistryType type, T emptyDefault) {
        super(type, emptyDefault);
    }

}
