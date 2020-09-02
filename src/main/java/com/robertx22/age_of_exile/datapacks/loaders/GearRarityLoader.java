package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.rarities.containers.GearRarities;
import com.robertx22.age_of_exile.datapacks.generators.RarityGenerator;

public class GearRarityLoader extends BaseRarityDatapackLoader<IGearRarity> {

    public static String ID = "rarity/gear";

    public GearRarityLoader() {
        super(Rarities.Gears, ID, x -> new GearRarities.CommonGear()
            .fromJson(x));
    }

    @Override
    public RarityGenerator getDatapackGenerator() {
        return new RarityGenerator(Rarities.Gears.getSeriazables(), ID);
    }

}
