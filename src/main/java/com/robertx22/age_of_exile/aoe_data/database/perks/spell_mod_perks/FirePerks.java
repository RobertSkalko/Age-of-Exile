package com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.FireSpellModStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.PerSpellDamageStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.PerSpellProjectileSpeedStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class FirePerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of(FireSpellModStats.THROW_FLAMES_FASTER, Spells.THROW_FLAMES)
            .addStat(new OptScaleExactStat(50, new PerSpellProjectileSpeedStat(Spells.THROW_FLAMES), ModType.FLAT))
            .addStat(new OptScaleExactStat(10, new PerSpellDamageStat(Spells.THROW_FLAMES), ModType.FLAT))
            .build();
        of(FireSpellModStats.THROW_FLAMES_BURN, Spells.THROW_FLAMES).build();
        of(FireSpellModStats.THROW_FLAMES_LIFESTEAL, Spells.THROW_FLAMES).build();

        of(FireSpellModStats.MAGMA_FLOWER_BURN, Spells.MAGMA_FLOWER)
            .build();
        of(FireSpellModStats.MAGMA_FLOWER_HEAL, Spells.MAGMA_FLOWER)
            .build();

    }

    private PerkBuilder of(MarkerStat stat, Spell spell) {
        return PerkBuilder.spellModifier(stat, spell);
    }

}
