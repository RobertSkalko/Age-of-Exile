package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.gears.CommonGear;
import com.robertx22.mine_and_slash.datapacks.generators.RarityGenerator;

public class GearRarityLoader extends BaseRarityDatapackLoader<GearRarity> {

    public static String ID = "rarity/gear";

    public GearRarityLoader() {
        super(Rarities.Gears, ID, x -> CommonGear.getInstance()
            .fromJson(x));
    }

    @Override
    public RarityGenerator getDatapackGenerator() {
        return new RarityGenerator(Rarities.Gears.getSeriazables(), ID);
    }

}
