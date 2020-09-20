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
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseLeatherArmors implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOOTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PANTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CHESTS;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HELMETS;

    @Override
    public void registerAll() {

        BOOTS = BaseGearBuilder.of(GearSlots.BOOTS, "leather_boots", "Boots", ModRegistry.GEAR_ITEMS.LEATHER_BOOTS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.boots, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(15, 40, DodgeRating.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

        PANTS = BaseGearBuilder.of(GearSlots.PANTS, "leather_pants", "Pants", ModRegistry.GEAR_ITEMS.LEATHER_PANTS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.pants, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(45, 80, DodgeRating.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

        CHESTS = BaseGearBuilder.of(GearSlots.CHEST, "leather_chest", "Vest", ModRegistry.GEAR_ITEMS.LEATHER_CHESTS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.chest, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(45, 80, DodgeRating.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

        HELMETS = BaseGearBuilder.of(GearSlots.HELMET, "leather_helmet", "Cap", ModRegistry.GEAR_ITEMS.LEATHER_HELMETS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.leather, SlotTag.helmet, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(25, 60, DodgeRating.getInstance(), ModType.FLAT))
            .addHunterLevelRanges()
            .build();

    }
}
