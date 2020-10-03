package com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.ArcaneSpellModStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.PerSpellCooldownStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ArcanePerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of(ArcaneSpellModStats.TP_DMG, Spells.TELEPORT).build();
        of(ArcaneSpellModStats.TP_ELE_RES, Spells.TELEPORT).build();
        of(ArcaneSpellModStats.TP_FASTER, Spells.TELEPORT)
            .addStat(new OptScaleExactStat(-25, new PerSpellCooldownStat(Spells.TELEPORT), ModType.FLAT))
            .build();

        of(ArcaneSpellModStats.MANA_COOLDOWN, Spells.AWAKEN_MANA)
            .addStat(new OptScaleExactStat(-25, new PerSpellCooldownStat(Spells.AWAKEN_MANA), ModType.FLAT))
            .build();
        of(ArcaneSpellModStats.MANA_ELE_RES, Spells.AWAKEN_MANA).build();
        of(ArcaneSpellModStats.MANA_MAGIC_SHIELD, Spells.AWAKEN_MANA).build();

    }

    private PerkBuilder of(MarkerStat stat, Spell spell) {
        return PerkBuilder.spellModifier(stat, spell);
    }

}
