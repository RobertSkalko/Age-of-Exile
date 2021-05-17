package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class Spells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new TestSpells().registerAll();
        new LoseAggroSpells().registerAll();
        new CurseSpells().registerAll();
        new SongSpells().registerAll();
        new DexSpells().registerAll();
        new IntSpells().registerAll();
        new StrSpells().registerAll();
        new AuraSpells().registerAll();
        new BossSpells().registerAll();
        new TotemSpells().registerAll();
    }
}
