package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsUnderExileEffect extends StatCondition {

    EffectSides side;
    String effect;

    public IsUnderExileEffect(EffectCtx ctx, EffectSides side) {
        super("is_" + side.id + "_under_" + ctx.id, "is_under_exile_effect");
        this.side = side;
    }

    IsUnderExileEffect() {
        super("", "is_under_exile_effect");
    }

    @Override
    public boolean can(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        return event.getSide(side)
            .hasStatusEffect(Database.ExileEffects()
                .get(effect)
                .getStatusEffect());
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsUnderExileEffect.class;
    }

}
