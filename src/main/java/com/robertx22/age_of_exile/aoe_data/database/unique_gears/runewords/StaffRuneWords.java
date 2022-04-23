package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class StaffRuneWords implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "heavenly_rainbow",
                "Heavenly Rainbow",
                BaseGearKeys.STAFF)
            .setReplacesName()
            .baseStats(
                Arrays.asList(
                    this.getAttackDamageStat(WeaponTypes.staff, Number.THIRD, Elements.Water),
                    this.getAttackDamageStat(WeaponTypes.staff, Number.THIRD, Elements.Fire),
                    this.getAttackDamageStat(WeaponTypes.staff, Number.THIRD, Elements.Earth)
                )
            )
            .stats(Arrays.asList(
                new StatModifier(-25, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Water)),
                new StatModifier(-25, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
                new StatModifier(-25, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Earth))
            ))
            .makeRuneWordOnly()
            .devComment("all ele high rng staff")
            .build();

    }
}
