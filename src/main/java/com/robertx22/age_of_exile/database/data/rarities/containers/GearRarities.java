package com.robertx22.age_of_exile.database.data.rarities.containers;

import com.robertx22.age_of_exile.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.RarityTypeEnum;
import com.robertx22.age_of_exile.database.data.rarities.gears.*;

public class GearRarities extends BaseRaritiesContainer<GearRarity> {

    public GearRarities() {
        super();
        add(CommonGear.getInstance());
        add(MagicalGear.getInstance());
        add(RareGear.getInstance());
        add(UniqueGear.getInstance());
        add(RelicGear.getInstance());
        this.onInit();
    }

    @Override
    public RarityTypeEnum getType() {
        return RarityTypeEnum.GEAR;
    }

}
