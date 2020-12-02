package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.spells.components.AuraSpellData;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CastSpeed;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.Arrays;

public class AuraSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.aura("haste", "Haste Aura", new AuraSpellData(0.25F,
            Arrays.asList(
                new StatModifier(55, 80, CastSpeed.getInstance()),
                new StatModifier(4, 15, AttackSpeed.getInstance())
            )))
            .build();

    }
}
