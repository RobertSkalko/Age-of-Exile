package com.robertx22.mine_and_slash.database.data.requirements.bases;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

public class GearRequestedFor {

    public BaseGearType forSlot;
    public GearItemData gearData;

    public GearRequestedFor(GearItemData data) {
        this.forSlot = data.GetBaseGearType();
        this.gearData = data;
    }

    public GearRequestedFor(BaseGearType slot) {
        this.forSlot = slot;
        this.gearData = new GearItemData();
    }

}
