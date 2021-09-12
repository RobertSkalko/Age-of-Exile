package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueNecklaces implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "rabbit_paw",
                "Rabbit's Paw",
                BaseGearTypes.NECKLACE)
            .stats(Arrays.asList(
                new StatModifier(2, 10, DatapackStats.MOVE_SPEED, ModType.FLAT),
                new StatModifier(5, 25, EnergyRegen.getInstance(), ModType.PERCENT),
                new StatModifier(2, 6, DatapackStats.AGI, ModType.FLAT)
            ))
            .setReplacesName()
            .build();

        UniqueGearBuilder.of(
                "ghast_necklace",
                "Ghast Tear",
                BaseGearTypes.NECKLACE)

            .stats(Arrays.asList(new StatModifier(15, 25, new ElementalResist(Elements.Water)),
                new StatModifier(15, 25, new ElementalResist(Elements.Fire)),
                new StatModifier(25, 25, DatapackStats.MANA_PER_10_INT, ModType.FLAT),
                new StatModifier(5, 15, ManaRegen.getInstance(), ModType.PERCENT),
                new StatModifier(3, 5, SpellDamage.getInstance(), ModType.FLAT),
                new StatModifier(1, 3, DatapackStats.VIT, ModType.FLAT),
                new StatModifier(2, 6, DatapackStats.AGI, ModType.FLAT)

            ))

            .build();

        UniqueGearBuilder.of(
                "skull_of_spirits",
                "Skull of Spirits",
                BaseGearTypes.NECKLACE)
            .stats(Arrays.asList(
                new StatModifier(1, 2, AllAttributes.getInstance(), ModType.FLAT),
                new StatModifier(10, 20, ManaRegen.getInstance(), ModType.PERCENT),
                new StatModifier(-5, -15, new ElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(-5, -15, new ElementalResist(Elements.Fire), ModType.FLAT)
            ))
            .build();

    }
}
