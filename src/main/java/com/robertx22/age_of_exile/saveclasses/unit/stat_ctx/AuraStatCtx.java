package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.List;

public class AuraStatCtx extends StatContext {

    Spell spell;

    public AuraStatCtx(Spell spell, List<ExactStatData> stats) {
        super(StatCtxType.AURA, stats);
        this.spell = spell;
    }
}
