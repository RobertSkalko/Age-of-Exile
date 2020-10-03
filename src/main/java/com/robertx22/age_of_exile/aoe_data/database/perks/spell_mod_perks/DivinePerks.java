package com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.DivineSpellModStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.PerSpellRestorationStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class DivinePerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of(DivineSpellModStats.FLOWER_CURE, Spells.HOLY_FLOWER).build();
        of(DivineSpellModStats.FLOWER_DMG, Spells.HOLY_FLOWER).build();
        of(DivineSpellModStats.FLOWER_RESTORE, Spells.HOLY_FLOWER)
            .addStat(new OptScaleExactStat(100, new PerSpellRestorationStat(Spells.HOLY_FLOWER), ModType.FLAT))
            .build();

    }

    private PerkBuilder of(MarkerStat stat, Spell spell) {
        return PerkBuilder.spellModifier(stat, spell);
    }

}
