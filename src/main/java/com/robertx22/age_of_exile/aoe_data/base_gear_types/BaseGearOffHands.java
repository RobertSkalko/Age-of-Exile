package com.robertx22.age_of_exile.aoe_data.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearOffHands implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> ARMOR_SHIELD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> MS_SHIELD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> DODGE_SHIELD;

    @Override
    public void registerAll() {

        ARMOR_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "tower_shield", "Tower Shield", ModRegistry.GEAR_ITEMS.ARMOR_SHIELDS)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.shield, SlotTag.plate, SlotTag.offhand_family, SlotTag.armor_stat, SlotTag.strength))
            .baseStat(new StatModifier(20, 80, Armor.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        MS_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "spirit_shield", "Spirit Shield", ModRegistry.GEAR_ITEMS.MS_SHIELDS)
            .req(new StatRequirement().intelligence(0.5f))
            .tags(new TagList(SlotTag.shield, SlotTag.cloth, SlotTag.offhand_family, SlotTag.magic_shield_stat, SlotTag.intelligence))
            .baseStat(new StatModifier(2, 5, MagicShield.getInstance(), ModType.FLAT))
            .implicitStat(new StatModifier(3, 8, SpellDamage.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        DODGE_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "buckler_shield", "Buckler Shield", ModRegistry.GEAR_ITEMS.DODGE_SHIELDS)
            .req(new StatRequirement().dexterity(0.5f))
            .tags(new TagList(SlotTag.shield, SlotTag.leather, SlotTag.offhand_family, SlotTag.dodge_stat, SlotTag.dexterity))
            .baseStat(new StatModifier(20, 80, DodgeRating.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

    }
}
