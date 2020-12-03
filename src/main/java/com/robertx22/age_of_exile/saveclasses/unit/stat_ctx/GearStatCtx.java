package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

import java.util.List;

public class GearStatCtx extends StatContext {

    GearItemData gear;
    GearSlot slot;

    public GearStatCtx(GearItemData gear, List<ExactStatData> stats) {
        super(StatCtxType.GEAR, stats);

        this.gear = gear;
        this.slot = gear.GetBaseGearType()
            .getGearSlot();
    }

}
