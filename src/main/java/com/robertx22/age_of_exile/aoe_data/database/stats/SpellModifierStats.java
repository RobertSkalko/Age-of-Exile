package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SpellModifierStats implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new OceanSpellModStats().registerAll();
        new NatureSpellModStats().registerAll();
        new FireSpellModStats().registerAll();
        new ArcaneSpellModStats().registerAll();
        new HunterSpellModStats().registerAll();
        new DivineSpellModStats().registerAll();

    }
}
