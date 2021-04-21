package com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists;

import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class WeightedMobEntry {

    public int weight = 1000;
    public String id = "";

    public WeightedMobEntry(int weight, EntityType<?> type) {
        this.weight = weight;
        this.id = Registry.ENTITY_TYPE.getId(type)
            .toString();
    }

    public WeightedMobEntry() {
    }
}
