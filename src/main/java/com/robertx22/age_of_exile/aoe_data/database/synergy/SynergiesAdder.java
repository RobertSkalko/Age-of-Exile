package com.robertx22.age_of_exile.aoe_data.database.synergy;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellAndEffect;
import com.robertx22.age_of_exile.aoe_data.database.stats.SynergyStats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SynergiesAdder implements ExileRegistryInit {

    public static String FROSTBALL_CHILL = "frostball_chill";

    @Override
    public void registerAll() {

        SynergyBuilder.of(FROSTBALL_CHILL, "Chilling Balls", WaterSpells.FROSTBALL_ID, 8, new StatModifier(5, 20, SynergyStats.SPELl_CHANCE_TO_APPLY_ELEMENTAL_EFFECT.get(new SpellAndEffect(WaterSpells.FROSTBALL_ID, NegativeEffects.CHILL))));

    }

}
