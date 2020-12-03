package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.AuraSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.DexSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.IntSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.StrSpells;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class Spells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new DexSpells().registerAll();
        new IntSpells().registerAll();
        new StrSpells().registerAll();
        new AuraSpells().registerAll();
    }
}
