package com.robertx22.age_of_exile.database.data.stats.types.resources.aura;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class IncreasedEffectOfAuras extends Stat implements IStatCtxModifier {

    public static String GUID = "inc_aura_effect";

    public static IncreasedEffectOfAuras getInstance() {
        return IncreasedEffectOfAuras.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases effect of auras.";
    }

    private IncreasedEffectOfAuras() {
        this.base_val = 0;
        this.min_val = -500;
        this.max_val = 500;
        this.scaling = StatScaling.NONE;
        this.statGroup = StatGroup.Misc;

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Aura Effect Strength";
    }

    @Override
    public void modify(ExactStatData thisStat, StatContext target) {
        float multi = 1F + thisStat.getAverageValue() / 100F;
        target.stats.forEach(x -> {
            x.multiplyBy(multi);
        });
    }

    @Override
    public StatContext.StatCtxType getCtxTypeNeeded() {
        return StatContext.StatCtxType.AURA;
    }

    private static class SingletonHolder {
        private static final IncreasedEffectOfAuras INSTANCE = new IncreasedEffectOfAuras();
    }
}

