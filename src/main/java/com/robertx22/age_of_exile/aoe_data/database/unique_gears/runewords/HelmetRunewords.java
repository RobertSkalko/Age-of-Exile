package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class HelmetRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "insight",
                "Insight",
                BaseGearKeys.HELMET)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(20, 30, ManaRegen.getInstance(), ModType.PERCENT),
                new StatModifier(10, 25, Mana.getInstance(), ModType.FLAT),
                new StatModifier(10, 25, new ElementalResist(Elements.Fire), ModType.PERCENT)
            ))
            .makeRuneWordOnly()
            .devComment("Mana focused caster helmet")
            .build();

    }
}

