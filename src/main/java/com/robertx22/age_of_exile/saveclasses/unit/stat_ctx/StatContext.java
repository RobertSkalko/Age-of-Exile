package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.List;

public abstract class StatContext {

    public final StatCtxType type;

    public List<ExactStatData> stats;

    public StatContext(StatCtxType type, List<ExactStatData> stats) {
        this.type = type;
        this.stats = stats;
    }

    public enum StatCtxType {
        GEAR, BASE_STAT, TALENT, POTION_EFFECT, AURA, MISC, MOB_AFFIX
    }
}
