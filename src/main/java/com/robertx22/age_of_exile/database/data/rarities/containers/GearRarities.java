package com.robertx22.age_of_exile.database.data.rarities.containers;

import com.robertx22.age_of_exile.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.RarityTypeEnum;
import com.robertx22.age_of_exile.database.data.rarities.gears.CommonGear;
import com.robertx22.age_of_exile.database.data.rarities.gears.MagicalGear;
import com.robertx22.age_of_exile.database.data.rarities.gears.RareGear;
import com.robertx22.age_of_exile.database.data.rarities.gears.UniqueGear;

public class GearRarities extends BaseRaritiesContainer<GearRarity> {

    public GearRarities() {
        super();
        add(CommonGear.getInstance());
        add(MagicalGear.getInstance());
        add(RareGear.getInstance());
        add(UniqueGear.getInstance());

        this.onInit();
    }

    @Override
    public RarityTypeEnum getType() {
        return RarityTypeEnum.GEAR;
    }

}
