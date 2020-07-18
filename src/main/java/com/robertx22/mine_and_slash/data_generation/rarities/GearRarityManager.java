package com.robertx22.mine_and_slash.data_generation.rarities;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.gears.CommonGear;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.RarityProvider;
import net.minecraft.data.DataGenerator;

public class GearRarityManager extends BaseRarityDatapackManager<GearRarity> {

    public static String ID = "rarity/gear";

    public GearRarityManager() {
        super(Rarities.Gears, ID, x -> CommonGear.getInstance()
            .fromJson(x));
    }

    @Override
    public RarityProvider getProvider(DataGenerator gen) {
        return new RarityProvider(gen, Rarities.Gears.getAllRarities(), ID);
    }

}
