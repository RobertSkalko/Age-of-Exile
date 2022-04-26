package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

public interface IGearPart {

    public enum Part {
        AFFIX, BASE_STATS, UNIQUE_STATS, SOCKETS, OTHER;
    }

    Part getPart();

    default MinMax getMinMax(GearItemData gear) {
        return gear.getRarity()
            .getStatPercentsFor(getPart());
    }

}
