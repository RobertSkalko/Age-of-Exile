package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runic_spells;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.all_keys.RunicSpellKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class ArmorRunicSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                RunicSpellKeys.TEST_RUNIC_SPELL.id,
                "Bone Test",
                BaseGearKeys.BOW)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(10, 30, Stats.CRIT_DAMAGE.get()),
                new StatModifier(10, 20, Stats.DAMAGE_TO_UNDEAD.get()),
                new StatModifier(-6, -3, Stats.LIFESTEAL.get())
            ))
            .randomAffixes(2)
            .makeRunicSpell()
            .build();
    }
}
