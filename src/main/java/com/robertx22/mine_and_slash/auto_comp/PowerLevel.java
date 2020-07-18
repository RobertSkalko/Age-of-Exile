package com.robertx22.mine_and_slash.auto_comp;

import com.google.common.collect.Multimap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.config.forge.parts.AutoCompatibleItemConfig;
import com.robertx22.mine_and_slash.config.forge.parts.AutoConfigItemType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PowerLevel {

    public static HashMap<Item, Types> CACHED = new HashMap<>();

    public PowerLevel(Item item, BaseGearType slot) {

        try {
            this.item = item;

            Multimap<String, EntityAttributeModifier> stats = item.getModifiers(slot.getVanillaSlotType());

            this.statAmount = stats.size();

            int MAX_SINGLE_STAT_VALUE = ModConfig.INSTANCE.autoCompatibleItems.MAX_SINGLE_STAT_VALUE.get();
            int MAX_TOTAL_STATS = ModConfig.INSTANCE.autoCompatibleItems.MAX_TOTAL_STATS.get();

            this.totalStatNumbers = stats.values()
                .stream()
                .mapToInt(x -> (int) MathHelper.clamp(x.getAmount(), -MAX_SINGLE_STAT_VALUE, MAX_SINGLE_STAT_VALUE))
                .sum();

            totalStatNumbers = MathHelper.clamp(totalStatNumbers, -MAX_TOTAL_STATS, MAX_TOTAL_STATS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static float getFloatValueOf(Item item) {
        List<BaseGearType> slots = SlashRegistry.GearTypes()
            .getList()
            .stream()
            .filter(x -> BaseGearType.isGearOfThisType(x, item))
            .collect(Collectors.toList());

        float val = 0;

        for (BaseGearType slot : slots) {
            PowerLevel power = new PowerLevel(item, slot);

            PowerLevel best = DeterminePowerLevels.STRONGEST.getOrDefault(slot, new PowerLevel(item, slot));

            val += power.divideBy(best);

        }

        val *= slots.size();

        return val;
    }

    public enum Types {
        HORRIBLE() {
            @Override
            public AutoConfigItemType getConfig() {
                return ModConfig.INSTANCE.autoCompatibleItems.HORRIBLE;
            }
        },
        TRASH() {
            @Override
            public AutoConfigItemType getConfig() {
                return ModConfig.INSTANCE.autoCompatibleItems.TRASH;
            }
        }, NORMAL() {
            @Override
            public AutoConfigItemType getConfig() {
                return ModConfig.INSTANCE.autoCompatibleItems.NORMAL;
            }
        }, BEST() {
            @Override
            public AutoConfigItemType getConfig() {
                return ModConfig.INSTANCE.autoCompatibleItems.BEST;
            }
        };

        Types() {

        }

        public abstract AutoConfigItemType getConfig();

    }

    public static Types getPowerClassification(Item item) {

        if (CACHED.containsKey(item)) {
            return CACHED.get(item);
        }

        float val = getFloatValueOf(item);

        AutoCompatibleItemConfig config = ModConfig.INSTANCE.autoCompatibleItems;

        Types type = null;

        if (val > config.BEST.POWER_REQ.get()) {
            type = Types.BEST;
        }
        if (val > config.NORMAL.POWER_REQ.get()) {
            type = Types.NORMAL;
        }
        if (val > config.TRASH.POWER_REQ.get()) {
            type = Types.TRASH;
        }

        if (type == null) {
            type = Types.HORRIBLE;
        }

        CACHED.put(item, type);

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
