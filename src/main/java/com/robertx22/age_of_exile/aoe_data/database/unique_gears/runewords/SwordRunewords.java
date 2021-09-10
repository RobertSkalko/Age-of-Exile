package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SwordRunewords implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "skull_cleaver",
                "Skull Cleaver",
                BaseGearTypes.SWORD)
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    this.getAttackDamageStat(WeaponTypes.sword, Number.HALF, Elements.Physical),
                    this.getAttackDamageStat(WeaponTypes.sword, Number.HALF, Elements.Water)
                )
            )
            .stats(Arrays.asList(
                new StatModifier(15, 25, Stats.DAMAGE_TO_UNDEAD.get(), ModType.FLAT),
                new StatModifier(1, 10, Stats.CRIT_CHANCE.get(), ModType.FLAT),
                new StatModifier(5, 25, Stats.INCREASED_LEECH.get(), ModType.FLAT)
            ))
            .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.ANO, RuneItem.RuneType.TOQ, RuneItem.RuneType.MOS, RuneItem.RuneType.HAR))
            .devComment("undead dmg / leech multi")
            .build();

    }
}
