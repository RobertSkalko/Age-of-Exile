package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class HelmetUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "mana_dominion",
                "Dominion of Mana",
                BasePlateArmors.HELMETS)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(25, 50, Mana.getInstance(), ModType.PERCENT),
                new StatModifier(3, 10, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT),
                new StatModifier(-10, -25, Health.getInstance(), ModType.PERCENT),
                new StatModifier(1, 10, DatapackStats.MANA_PER_10_INT, ModType.FLAT)
            ))
            .devComment("mana crit helmet")
            .build();
    }
}
