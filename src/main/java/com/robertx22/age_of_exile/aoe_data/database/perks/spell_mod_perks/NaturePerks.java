package com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.NatureSpellModStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.PerSpellCooldownStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.PerSpellManaCostStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class NaturePerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of(NatureSpellModStats.FAST_BUSH, Spells.POISON_BUSH)
            .addStat(new OptScaleExactStat(-25, new PerSpellCooldownStat(Spells.POISON_BUSH), ModType.FLAT))
            .build();
        of(NatureSpellModStats.POISON_BUSH, Spells.POISON_BUSH).build();
        of(NatureSpellModStats.SLOW_BUSH, Spells.POISON_BUSH).build();

        of(NatureSpellModStats.THORN_ARMOR_THORNS, Spells.THORN_ARMOR)
            .addStat(new OptScaleExactStat(25, new PerSpellManaCostStat(Spells.THORN_ARMOR), ModType.FLAT))
            .build();

    }

    private PerkBuilder of(MarkerStat stat, Spell spell) {
        return PerkBuilder.spellModifier(stat, spell);
    }

}
