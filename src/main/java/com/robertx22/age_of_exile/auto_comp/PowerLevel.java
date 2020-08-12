package com.robertx22.age_of_exile.auto_comp;

import com.google.common.collect.Multimap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.config.forge.parts.AutoCompatibleItemConfig;
import com.robertx22.age_of_exile.config.forge.parts.AutoConfigItemType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PowerLevel {

    public static HashMap<Item, Float> CACHED = new HashMap<>();

    public PowerLevel(Item item, BaseGearType slot) {

        try {
            this.item = item;

            Multimap<EntityAttribute, EntityAttributeModifier> stats = item.getAttributeModifiers(slot.getVanillaSlotType());

            this.statAmount = stats.size();

            int MAX_SINGLE_STAT_VALUE = ModConfig.get().autoCompatibleItems.MAX_SINGLE_STAT_VALUE;
            int MAX_TOTAL_STATS = ModConfig.get().autoCompatibleItems.MAX_TOTAL_STATS;

            this.totalStatNumbers = stats.values()
                .stream()
                .mapToInt(x -> (int) MathHelper.clamp(x.getValue(), -MAX_SINGLE_STAT_VALUE, MAX_SINGLE_STAT_VALUE))
                .sum();

            totalStatNumbers = MathHelper.clamp(totalStatNumbers, -MAX_TOTAL_STATS, MAX_TOTAL_STATS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static float getFloatValueOf(Item item) {

        if (CACHED.containsKey(item)) {
            return CACHED.get(item);
        }

        List<BaseGearType> slots = SlashRegistry.GearTypes()
            .getList()
            .stream()
            .filter(x -> BaseGearType.isGearOfThisType(x, item))
            .collect(Collectors.toList());

        float val = 0;

        for (BaseGearType slot : slots) {
            PowerLevel power = new PowerLevel(item, slot);

            PowerLevel best = DeterminePowerLevels.STRONGEST.get(slot.getGearSlot());

            val += power.divideBy(best);

        }

        val /= slots.size();

        CACHED.put(item, val);

        return val;
    }

    public static AutoConfigItemType getPowerClassification(Float val) {

        AutoCompatibleItemConfig config = ModConfig.get().autoCompatibleItems;

        AutoConfigItemType type = null;

        if (config.TIER_5.isInRange(val)) {
            type = ModConfig.get().autoCompatibleItems.TIER_5;
        } else if (config.TIER_4.isInRange(val)) {
            type = ModConfig.get().autoCompatibleItems.TIER_4;
        } else if (config.TIER_3.isInRange(val)) {
            type = ModConfig.get().autoCompatibleItems.TIER_3;
        } else if (config.TIER_2.isInRange(val)) {
            type = ModConfig.get().autoCompatibleItems.TIER_2;
        } else if (config.TIER_1.isInRange(val)) {
            type = ModConfig.get().autoCompatibleItems.TIER_1;
        } else {
            type = ModConfig.get().autoCompatibleItems.TIER_0;

        }

        return type;
    }

    public static AutoConfigItemType getPowerClassification(Item item) {

        if (CACHED.containsKey(item)) {
            return getPowerClassification(CACHED.get(item));
        }

        AutoConfigItemType type = getPowerClassification(getFloatValueOf(item));

        CACHED.put(item, getFloatValueOf(item));

        return type;
    }

    public boolean isStrongerThan(PowerLevel other) {
        return totalStatNumbers > other.totalStatNumbers;
    }

    public float divideBy(PowerLevel other) {
        return totalStatNumbers / other.totalStatNumbers;
    }

    public Item item;
    public int statAmount = 0;
    public float totalStatNumbers = 0;

}
