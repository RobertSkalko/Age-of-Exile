package com.robertx22.age_of_exile.database.data;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

import static com.robertx22.age_of_exile.config.forge.ModConfig.get;

public class BaseRuneGem implements IGUID, IWeighted {

    public List<StatModifier> on_armor_stats = new ArrayList<>();

    public List<StatModifier> on_jewelry_stats = new ArrayList<>();

    public List<StatModifier> on_weapons_stats = new ArrayList<>();

    public String item_id = "";

    public String identifier = "";

    public float required_item_level;

    public int weight = 1000;

    public float effective_level;

    public int getEffectiveLevel() {
        return (int) (MathHelper.clamp(get().Server.MAX_LEVEL * effective_level, 1, get().Server.MAX_LEVEL));
    }

    public int getReqLevel() {
        return (int) (MathHelper.clamp(get().Server.MAX_LEVEL * required_item_level, 1, get().Server.MAX_LEVEL));
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
        return null;

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
