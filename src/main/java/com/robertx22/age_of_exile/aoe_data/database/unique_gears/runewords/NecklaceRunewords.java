package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.stats.PlusSkillsInSchoolStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class NecklaceRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "heavenly_tear",
                "Heavenly Tear",
                BaseGearKeys.NECKLACE)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(1, 2, PlusSkillsInSchoolStats.PLUS_DIVINE),
                new StatModifier(10, 20, Stats.HEAL_STRENGTH.get()),
                new StatModifier(5, 15, Armor.getInstance())
            ))
            .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.TOQ, RuneItem.RuneType.DOS, RuneItem.RuneType.YUN, RuneItem.RuneType.WIR))
            .devComment("divine school heal necklace")
            .build();

    }
}
