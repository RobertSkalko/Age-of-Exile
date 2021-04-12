package com.robertx22.age_of_exile.uncommon.auto_comp;

import com.google.common.collect.Multimap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.config.forge.parts.AutoCompatibleItemConfig;
import com.robertx22.age_of_exile.config.forge.parts.AutoConfigItemType;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemAutoPowerLevels {

    private static HashMap<String, ItemAutoPowerLevels> STRONGEST = new HashMap<>();
    public static HashMap<Item, AutoConfigItemType> CACHED = new HashMap<>();
    public static HashMap<Item, Float> CACHED_FLOATS = new HashMap<>();

    public ItemAutoPowerLevels(Item item, BaseGearType slot) {

        try {
            this.item = item;

            Multimap<EntityAttribute, EntityAttributeModifier> stats = item.getAttributeModifiers(slot.getVanillaSlotType());

            this.statAmount = stats.size();

            int MAX_SINGLE_STAT_VALUE = ModConfig.get().autoCompatibleItems.MAX_SINGLE_STAT_VALUE;
            int MAX_TOTAL_STATS = ModConfig.get().autoCompatibleItems.MAX_TOTAL_STATS;

            int sum = 0;
            for (EntityAttributeModifier x : stats.values()) {
                int clamp = (int) MathHelper.clamp(x.getValue(), -MAX_SINGLE_STAT_VALUE, MAX_SINGLE_STAT_VALUE);
                if (x.getName()
                    .equals(Registry.ATTRIBUTE.getId(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                        .toString())) {
                    clamp *= 2; // make dmg be more important than other stats
                }
                sum += clamp;
            }
            this.totalStatNumbers = sum;

            totalStatNumbers += item.getMaxDamage() / 250F; // some items only differ by durability, so make the more durable ones have higher value

            totalStatNumbers = MathHelper.clamp(totalStatNumbers, -MAX_TOTAL_STATS, MAX_TOTAL_STATS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static float getFloatValueOf(Item item) {

        if (STRONGEST.isEmpty()) {
            return 0F;
        }

        if (CACHED_FLOATS.containsKey(item)) {
            return CACHED_FLOATS.get(item);
        }

        List<BaseGearType> slots = Database.GearTypes()
            .getList()
            .stream()
            .filter(x -> BaseGearType.isGearOfThisType(x, item))
            .collect(Collectors.toList());

        float val = 0;

        for (BaseGearType slot : slots) {
            ItemAutoPowerLevels power = new ItemAutoPowerLevels(item, slot);

            ItemAutoPowerLevels best = getStrongestOf(slot.getGearSlot());

            if (best == null) {
                System.out.println("No best item for slot: " + slot.getGearSlot()
                    .GUID());
                return 0F;
            }

            val += power.divideBy(best);

        }

        val /= slots.size();

        CACHED_FLOATS.put(item, val);

        return val;
    }

    @Nullable
    public static AutoConfigItemType getHandCustomizedType(Item item) {

        if (!ModConfig.get().autoCompatibleItems.ENABLE_MANUAL_TWEAKS) {
            return null;
        }

        if (item == Items.BOW || item == Items.CROSSBOW) {
            return ModConfig.get().autoCompatibleItems.TIER_0;
        }

        if (item instanceof ToolItem) {
            ToolItem tool = (ToolItem) item;
            ToolMaterial mat = tool.getMaterial();

            if (mat == ToolMaterials.WOOD) {
                return ModConfig.get().autoCompatibleItems.WOOD;
            }
            if (mat == ToolMaterials.STONE) {
                return ModConfig.get().autoCompatibleItems.STONE;
            }

        } else if (item instanceof ArmorItem) {
            ArmorItem tool = (ArmorItem) item;
            ArmorMaterial mat = tool.getMaterial();

            if (mat == ArmorMaterials.LEATHER) {
                return ModConfig.get().autoCompatibleItems.LEATHER;
            }

        }

        return null;
    }

    public static AutoConfigItemType getPowerClassification(Float val) {

        AutoCompatibleItemConfig config = ModConfig.get().autoCompatibleItems;

        AutoConfigItemType type = null;

        if (config.TIER_5.isInRange(val)) {
            type = config.TIER_5;
        } else if (config.TIER_4.isInRange(val)) {
            type = config.TIER_4;
        } else if (config.TIER_3.isInRange(val)) {
            type = config.TIER_3;
        } else if (config.TIER_2.isInRange(val)) {
            type = config.TIER_2;
        } else if (config.TIER_1.isInRange(val)) {
            type = config.TIER_1;
        } else {
            type = config.TIER_0;
        }

        return type;
    }

    public static AutoConfigItemType getPowerClassification(Item item) {

        if (CACHED.containsKey(item)) {
            return CACHED.get(item);
        }

        AutoConfigItemType handmade = getHandCustomizedType(item);

        if (handmade != null) {
            return handmade;
        }

        AutoConfigItemType type = getPowerClassification(getFloatValueOf(item));

        CACHED.put(item, type);

        return type;
    }

    public boolean isStrongerThan(ItemAutoPowerLevels other) {
        return totalStatNumbers > other.totalStatNumbers;
    }

    public float divideBy(ItemAutoPowerLevels other) {
        return totalStatNumbers / other.totalStatNumbers;
    }

    public Item item;
    public int statAmount = 0;
    public float totalStatNumbers = 0;

    public static ItemAutoPowerLevels getStrongestOf(GearSlot slot) {
        return STRONGEST.get(slot.GUID());
    }

    public static void setupHashMaps() {

        Set<BaseGearType> types = new HashSet<>(Database.GearTypes()
            .getList());

        Watch watch = new Watch();

        Registry.ITEM
            .stream()
            .filter(x -> {
                return !Ref.MODID.equals(Registry.ITEM.getId(x)
                    .getNamespace()) && !(x instanceof BlockItem);
            })
            .forEach(item -> {
                try {

                    for (BaseGearType slot : types) {
                        if (BaseGearType.isGearOfThisType(slot, item)) {

                            ItemAutoPowerLevels current = new ItemAutoPowerLevels(item, slot);

                            ItemAutoPowerLevels strongest = STRONGEST.getOrDefault(slot.GUID(), current);

                            if (current.isStrongerThan(strongest)) {
                                strongest = current;
                            }

                            STRONGEST.put(slot.getGearSlot()
                                .GUID(), strongest);

                            break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        watch.print("[Setting up auto compatibility config power levels] ");

    }

}
