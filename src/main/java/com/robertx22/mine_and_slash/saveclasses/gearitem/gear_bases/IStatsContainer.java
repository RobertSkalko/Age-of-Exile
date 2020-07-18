package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

import java.util.List;

public interface IStatsContainer {

    List<ExactStatData> GetAllStats(GearItemData gear);

    default boolean isBaseStats() {
        return false;
    }

}
