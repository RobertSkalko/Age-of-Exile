package com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.HunterSpellModStats;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class HunterPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of(HunterSpellModStats.IMBUE_CRIT, Spells.IMBUE).build();
        of(HunterSpellModStats.IMBUE_ELE, Spells.IMBUE).build();
        of(HunterSpellModStats.IMBUE_PHYS, Spells.IMBUE)
            .build();

    }

    private PerkBuilder of(MarkerStat stat, Spell spell) {
        return PerkBuilder.spellModifier(stat, spell);
    }

}
