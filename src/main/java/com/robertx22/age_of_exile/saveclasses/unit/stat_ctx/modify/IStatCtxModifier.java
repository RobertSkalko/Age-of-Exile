package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;

public interface IStatCtxModifier {

    void modify(ExactStatData thisStat, StatContext target);

    StatContext.StatCtxType getCtxTypeNeeded();

}
