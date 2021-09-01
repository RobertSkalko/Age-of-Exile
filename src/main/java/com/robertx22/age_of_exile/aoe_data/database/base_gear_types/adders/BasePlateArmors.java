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

public class BasePlateArmors implements ExileRegistryInit, GearDataHelper {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOOTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PANTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CHESTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HELMETS;

    @Override
    public void registerAll() {

        BOOTS = BaseGearBuilder.of(GearSlots.BOOTS, "plate_boots", "Footwear")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.plate, SlotTag.boots, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.BOOTS),
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.BOOTS)
            )
            .addFullLevelRange()
            .build();

        PANTS = BaseGearBuilder.of(GearSlots.PANTS, "plate_pants", "Greaves")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.plate, SlotTag.pants, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.PANTS),
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.PANTS)
            )
            .addFullLevelRange()
            .build();

        CHESTS = BaseGearBuilder.of(GearSlots.CHEST, "plate_chest", "Chestplate")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.plate, SlotTag.chest, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.CHEST),
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.CHEST)
            )
            .addFullLevelRange()
            .build();

        HELMETS = BaseGearBuilder.of(GearSlots.HELMET, "plate_helmet", "Helmet")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.plate, SlotTag.helmet, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.HEALTH, ArmorType.HEAVY, ArmorSlot.HELMET),
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.HELMET)
            )
            .addFullLevelRange()
            .build();
    }
}


