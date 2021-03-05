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
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearJewelry implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> RING_MANA_REG;

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> POISON_RES_RING;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> COLD_RES_RING;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> THUNDER_RES_RING;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> FIRE_RES_RING;

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> ALL_RES_NECKLACE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HP_NECKLACE;

    static int minResist = 20;
    static int maxResist = 50;

    @Override
    public void registerAll() {

        RING_MANA_REG = BaseGearBuilder.of(GearSlots.RING, "mana_reg_ring", "Ring", ModRegistry.GEAR_ITEMS.MANA_REG_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.ARCANA)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(2, 4, Mana.getInstance(), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Occult")
            .addLvlRange(LevelRanges.MID_TO_END, "Arcana")
            .build();

        POISON_RES_RING = BaseGearBuilder.of(GearSlots.RING, "poison_res_ring", "Ring", ModRegistry.GEAR_ITEMS.POISON_RES_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.NATURE)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Nature), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Opal")
            .addLvlRange(LevelRanges.MID_TO_END, "Emerald")
            .build();

        COLD_RES_RING = BaseGearBuilder.of(GearSlots.RING, "cold_res_ring", "Ring", ModRegistry.GEAR_ITEMS.COLD_RES_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.WATER)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Water), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Aquamarine")
            .addLvlRange(LevelRanges.MID_TO_END, "Sapphire")
            .build();

        THUNDER_RES_RING = BaseGearBuilder.of(GearSlots.RING, "thunder_res_ring", "Ring", ModRegistry.GEAR_ITEMS.THUNDER_RES_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.THUNDER)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Thunder), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Agate")
            .addLvlRange(LevelRanges.MID_TO_END, "Topaz")
            .build();

        FIRE_RES_RING = BaseGearBuilder.of(GearSlots.RING, "fire_res_ring", "Ring", ModRegistry.GEAR_ITEMS.FIRE_RES_RINGS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.FIRE)
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Fire), ModType.FLAT))
            .addLvlRange(LevelRanges.START_TO_LOW, "Coral")
            .addLvlRange(LevelRanges.MID_TO_END, "Ruby")
            .build();

        ALL_RES_NECKLACE = BaseGearBuilder.of(GearSlots.NECKLACE, "all_res_necklace", "Necklace", ModRegistry.GEAR_ITEMS.ALL_RES_NECKLACES)
            .essenceItem(ModRegistry.GEAR_MATERIALS.ELEMENTAL)
            .tags(new TagList(SlotTag.necklace, SlotTag.jewelry_family))
            .baseStat(new StatModifier(5, 10, new ElementalResist(Elements.Elemental), ModType.FLAT))
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

