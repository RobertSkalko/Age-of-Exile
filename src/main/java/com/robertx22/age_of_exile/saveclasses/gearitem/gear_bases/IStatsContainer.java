package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

import java.util.List;

public interface IStatsContainer {

    List<ExactStatData> GetAllStats(GearItemData gear);

}
