package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellModifierStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SpellModifierPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.spellModifier(SpellModifierStats.CRASHING_ROCKS, Spells.TIDAL_WAVE)
            .build();
        PerkBuilder.spellModifier(SpellModifierStats.BURNING_CURRENTS, Spells.TIDAL_WAVE)
            .build();
        PerkBuilder.spellModifier(SpellModifierStats.CHILLING_TIDES, Spells.TIDAL_WAVE)
            .build();

    }
}
