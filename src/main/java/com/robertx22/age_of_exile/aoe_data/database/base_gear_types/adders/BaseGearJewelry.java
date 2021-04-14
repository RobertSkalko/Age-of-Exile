package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearJewelry implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> MANA_RING;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HP_RING;

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> ALL_RES_NECKLACE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HP_NECKLACE;

    @Override
    public void registerAll() {

        MANA_RING = BaseGearBuilder.of(GearSlots.RING, "mana_reg_ring", "Ring", ModRegistry.GEAR_ITEMS.MANA_REG_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.ARCANA)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(10, 20, Mana.getInstance(), ModType.LOCAL_INCREASE))
            .addLvlRange(LevelRanges.START_TO_LOW, "Occult")
            .addLvlRange(LevelRanges.MID_TO_END, "Arcana")
            .build();

        HP_RING = BaseGearBuilder.of(GearSlots.RING, "hp_ring", "Ring", ModRegistry.GEAR_ITEMS.HP_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.ARCANA)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(10, 20, HealthRegen.getInstance(), ModType.LOCAL_INCREASE))
            .addLvlRange(LevelRanges.START_TO_LOW, "Primitive")
            .addLvlRange(LevelRanges.MID_TO_END, "Primordial")
            .build();

        ALL_RES_NECKLACE = BaseGearBuilder.of(GearSlots.NECKLACE, "all_res_necklace", "Necklace", ModRegistry.GEAR_ITEMS.ALL_RES_NECKLACES)
            .essenceItem(ModRegistry.GEAR_MATERIALS.ELEMENTAL)
            .tags(new TagList(SlotTag.necklace, SlotTag.jewelry_family))
            .baseStat(new StatModifier(4, 8, new ElementalResist(Elements.Elemental), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Primitive")
            .addLvlRange(LevelRanges.MID_TO_END, "Primordial")
            .build();

        HP_NECKLACE = BaseGearBuilder.of(GearSlots.NECKLACE, "life_necklace", "Necklace", ModRegistry.GEAR_ITEMS.HP_NECKLACES)
            .essenceItem(ModRegistry.GEAR_MATERIALS.LIFE)
            .tags(new TagList(SlotTag.necklace, SlotTag.jewelry_family))
            .baseStat(new StatModifier(2, 6, Health.getInstance(), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Life Giving")
            .addLvlRange(LevelRanges.MID_TO_END, "Life Blood")
            .build();

    }
}

