package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class HelmetRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "insight",
                "Insight",
                BasePlateArmors.HELMETS.get(LevelRanges.FULL))
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(20, 30, ManaRegen.getInstance(), ModType.PERCENT),
                new StatModifier(10, 25, Mana.getInstance(), ModType.FLAT),
                new StatModifier(10, 25, new ElementalResist(Elements.Fire), ModType.FLAT)
            ))
            .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.ITA, RuneItem.RuneType.DOS, RuneItem.RuneType.TOQ))
            .devComment("Mana focused caster helmet")
            .build();

    }
}

