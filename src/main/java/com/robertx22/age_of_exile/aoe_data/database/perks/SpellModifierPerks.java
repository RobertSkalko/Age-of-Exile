package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SpellModifierPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new OceanPerks().registerAll();
        new NaturePerks().registerAll();
        new FirePerks().registerAll();
        new ArcanePerks().registerAll();
        new HunterPerks().registerAll();
        new DivinePerks().registerAll();

    }
}
