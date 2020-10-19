package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.EquipmentSlot;

import java.util.HashMap;

import static com.robertx22.age_of_exile.uncommon.utilityclasses.SlotUtils.multiOf;

public class BaseLeatherArmors implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOOTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PANTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CHESTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HELMETS;

    static float min = 20;
    static float max = 80;

    static float minHP = 1;
    static float maxHP = 3;

    @Override
    public void registerAll() {

        BOOTS = BaseGearBuilder.of(GearSlots.BOOTS, "leather_boots", "Boots", ModRegistry.GEAR_ITEMS.LEATHER_BOOTS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.boots, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.FEET) * min, multiOf(EquipmentSlot.FEET) * max, DodgeRating.getInstance(), ModType.FLAT))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.FEET) * minHP, multiOf(EquipmentSlot.FEET) * maxHP, Health.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

        PANTS = BaseGearBuilder.of(GearSlots.PANTS, "leather_pants", "Pants", ModRegistry.GEAR_ITEMS.LEATHER_PANTS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.pants, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.LEGS) * min, multiOf(EquipmentSlot.LEGS) * max, DodgeRating.getInstance(), ModType.FLAT))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.LEGS) * minHP, multiOf(EquipmentSlot.LEGS) * maxHP, Health.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

        CHESTS = BaseGearBuilder.of(GearSlots.CHEST, "leather_chest", "Vest", ModRegistry.GEAR_ITEMS.LEATHER_CHESTS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.chest, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.CHEST) * min, multiOf(EquipmentSlot.CHEST) * max, DodgeRating.getInstance(), ModType.FLAT))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.CHEST) * minHP, multiOf(EquipmentSlot.CHEST) * maxHP, Health.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

        HELMETS = BaseGearBuilder.of(GearSlots.HELMET, "leather_helmet", "Cap", ModRegistry.GEAR_ITEMS.LEATHER_HELMETS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.helmet, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.HEAD) * min, multiOf(EquipmentSlot.HEAD) * max, DodgeRating.getInstance(), ModType.FLAT))
            .baseStat(new StatModifier(multiOf(EquipmentSlot.HEAD) * minHP, multiOf(EquipmentSlot.HEAD) * maxHP, Health.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

    }
}
