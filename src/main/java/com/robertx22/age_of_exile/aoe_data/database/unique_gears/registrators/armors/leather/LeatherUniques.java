package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.leather;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseLeatherArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class LeatherUniques implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.EXEC_PRIDE,
            "exec_pride",
            "Executioner's Pride",
            "To miss is not an option.",
            BaseLeatherArmors.CHESTS.get(LevelRanges.ENDGAME))
            .stats(Arrays.asList(
                new StatModifier(5, 15, DodgeRating.getInstance(), ModType.FLAT),
                new StatModifier(10, 40, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(5, 20, CriticalHit.getInstance(), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.HARBINGER_CHEST,
            "harbinger",
            "Harbinger",
            "His eyes like black holes, his armor had the color of horizon.",
            BaseLeatherArmors.CHESTS.get(LevelRanges.MIDDLE))
            .stats(Arrays.asList(
                new StatModifier(10, 100, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(2, 10, CriticalHit.getInstance(), ModType.FLAT),
                new StatModifier(25, 25, CriticalDamage.getInstance(), ModType.FLAT)))
            .build();

    }
}
