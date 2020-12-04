package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.List;

public class TalentStatCtx extends StatContext {

    Perk perk;
    PointData point;

    public TalentStatCtx(PointData point, Perk perk, List<ExactStatData> stats) {
        super(StatCtxType.TALENT, stats);
        this.perk = perk;
        this.point = point;
    }
}