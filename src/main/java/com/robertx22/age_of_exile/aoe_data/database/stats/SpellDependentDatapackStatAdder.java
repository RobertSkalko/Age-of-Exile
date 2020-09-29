package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.GiveSpellStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.SpecificSpellExtraProjectilesStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class SpellDependentDatapackStatAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new SpecificSpellExtraProjectilesStat(SlashRegistry.Spells()
            .getFromSerializables(new DataGenKey<Spell>(Spells.FIREBALL_ID))).addToSerializables();

        SlashRegistry.Spells()
            .getSerializable()
            .forEach(x -> {
                new GiveSpellStat(x).addToSerializables();
            });
    }
}
