package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BuffSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.buffSelfSpell(SpellKeys.ICE_SHIELD,
                SpellConfiguration.Builder.instant(20, 60), "Ice Shield",
                BeneficialEffects.FROST_ARMOR, 30)
            .build();

        SpellBuilder.buffAlliesSpell(SpellKeys.NATURE_BALM,
                SpellConfiguration.Builder.instant(20, 60), "Nature's Balm",
                BeneficialEffects.REGENERATE, 15)
            .build();

    }
}
