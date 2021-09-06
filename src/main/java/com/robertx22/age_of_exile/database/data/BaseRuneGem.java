package com.robertx22.age_of_exile.database.data;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;

import java.util.ArrayList;
import java.util.List;

public class BaseRuneGem implements IGUID, IWeighted {

    public List<StatModifier> on_armor_stats = new ArrayList<>();

    public List<StatModifier> on_jewelry_stats = new ArrayList<>();

    public List<StatModifier> on_weapons_stats = new ArrayList<>();

    public String item_id = "";

    public String identifier = "";

    public int tier = 1;
    public int weight = 1000;

    public float getEffectiveLevel() {
        return LevelUtils.tierToLevel(tier);
    }

    public int getReqLevel() {
        return LevelUtils.tierToLevel(tier);
    }

    public final List<StatModifier> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return on_armor_stats;
        }
        if (sfor == SlotFamily.Jewelry) {
            return on_jewelry_stats;
        }
        if (sfor == SlotFamily.Weapon) {
            return on_weapons_stats;
        }

        return on_armor_stats;

    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
