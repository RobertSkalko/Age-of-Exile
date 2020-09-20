package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BasePlateArmors implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOOTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PANTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CHESTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HELMETS;

    @Override
    public void registerAll() {

        BOOTS = BaseGearBuilder.of(GearSlots.BOOTS, "plate_boots", "Footwear", ModRegistry.GEAR_ITEMS.PLATE_BOOTS)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.plate, SlotTag.boots, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(new StatModifier(10, 30, Armor.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        PANTS = BaseGearBuilder.of(GearSlots.PANTS, "plate_pants", "Greaves", ModRegistry.GEAR_ITEMS.PLATE_PANTS)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.plate, SlotTag.pants, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(new StatModifier(20, 60, Armor.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        CHESTS = BaseGearBuilder.of(GearSlots.CHEST, "plate_chest", "Chestplate", ModRegistry.GEAR_ITEMS.PLATE_CHESTS)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.plate, SlotTag.chest, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(new StatModifier(30, 100, Armor.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        HELMETS = BaseGearBuilder.of(GearSlots.HELMET, "plate_helmet", "Helmet", ModRegistry.GEAR_ITEMS.PLATE_HELMETS)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.plate, SlotTag.helmet, SlotTag.armor_family, SlotTag.plate, SlotTag.strength))
            .baseStat(new StatModifier(15, 35, Armor.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();
    }
}
