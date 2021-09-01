package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class BaseLeatherArmors implements ExileRegistryInit, GearDataHelper {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOOTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PANTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CHESTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HELMETS;

    @Override
    public void registerAll() {

        BOOTS = BaseGearBuilder.of(GearSlots.BOOTS, "leather_boots", "Boots")
            .tags(new TagList(SlotTag.leather, SlotTag.boots, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.MEDIUM, ArmorSlot.BOOTS),
                getStat(ArmorStat.DODGE, ArmorType.MEDIUM, ArmorSlot.BOOTS)
            )
            .addFullLevelRange()
            .build();

        PANTS = BaseGearBuilder.of(GearSlots.PANTS, "leather_pants", "Pants")
            .tags(new TagList(SlotTag.leather, SlotTag.pants, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.MEDIUM, ArmorSlot.PANTS),
                getStat(ArmorStat.DODGE, ArmorType.MEDIUM, ArmorSlot.PANTS)
            )
            .addFullLevelRange()
            .build();

        CHESTS = BaseGearBuilder.of(GearSlots.CHEST, "leather_chest", "Vest")
            .tags(new TagList(SlotTag.leather, SlotTag.chest, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.MEDIUM, ArmorSlot.CHEST),
                getStat(ArmorStat.DODGE, ArmorType.MEDIUM, ArmorSlot.CHEST)
            )
            .addFullLevelRange()
            .build();

        HELMETS = BaseGearBuilder.of(GearSlots.HELMET, "leather_helmet", "Cap")
            .tags(new TagList(SlotTag.leather, SlotTag.helmet, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.MEDIUM, ArmorSlot.HELMET),
                getStat(ArmorStat.DODGE, ArmorType.MEDIUM, ArmorSlot.HELMET)
            )
            .addFullLevelRange()
            .build();

    }
}
