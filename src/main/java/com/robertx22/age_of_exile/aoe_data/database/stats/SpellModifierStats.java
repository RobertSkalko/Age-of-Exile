package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.FireSpellModStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.NatureSpellModStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.OceanSpellModStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SpellModifierStats implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new OceanSpellModStats().registerAll();
        new NatureSpellModStats().registerAll();
        new FireSpellModStats().registerAll();

    }
}
