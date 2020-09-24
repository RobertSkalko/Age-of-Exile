package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.GiveSpellStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class SpellDependentDatapackStatAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        SlashRegistry.Spells()
            .getSerializable()
            .forEach(x -> {
                new GiveSpellStat(x).addToSerializables();
            });
    }
}
