package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class BaseGearOffHands implements ExileRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> ARMOR_SHIELD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> MS_SHIELD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> DODGE_SHIELD;

    @Override
    public void registerAll() {

        ARMOR_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "tower_shield", "Tower Shield")
            .tags(new TagList(SlotTag.shield, SlotTag.plate, SlotTag.offhand_family, SlotTag.armor_stat, SlotTag.strength))
            .baseStat(new StatModifier(6, 12, Armor.getInstance(), ModType.FLAT))
            .addFullLevelRange()
            .build();

        MS_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "spirit_shield", "Spirit Shield")
            .tags(new TagList(SlotTag.shield, SlotTag.cloth, SlotTag.offhand_family, SlotTag.magic_shield_stat, SlotTag.intelligence))
            .baseStat(new StatModifier(5, 15, Mana.getInstance(), ModType.FLAT))
            .implicitStat(new StatModifier(5, 10, SpellDamage.getInstance(), ModType.FLAT))
            .addFullLevelRange()
            .build();

        DODGE_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "buckler_shield", "Buckler Shield")
            .tags(new TagList(SlotTag.shield, SlotTag.leather, SlotTag.offhand_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(5, 12, DodgeRating.getInstance(), ModType.FLAT))
            .addFullLevelRange()
            .build();

    }
}
