package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class BaseGearOffHands implements ExileRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> ARMOR_SHIELD;

    @Override
    public void registerAll() {

        ARMOR_SHIELD = BaseGearBuilder.of(GearSlots.SHIELD, "tower_shield", "Tower Shield")
            .tags(new TagList(SlotTag.shield, SlotTag.plate, SlotTag.offhand_family, SlotTag.armor_stat, SlotTag.strength))
            .baseStat(new StatModifier(6, 12, Armor.getInstance(), ModType.FLAT))
            .baseStat(new StatModifier(-25, -25, Stats.STYLE_DAMAGE.get(PlayStyle.magic)))
            .addFullLevelRange()
            .build();

    }
}
