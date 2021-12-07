package com.robertx22.age_of_exile.vanilla_mc.items.crates.rarity_gear;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISettableLevelTier;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class RarityGearCrateData implements ISettableLevelTier {

    @Store
    public Integer tier = 0;

    @Store
    public String rar = "";

    @Store
    public boolean set_to_player_lvl = true;

    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(rar);
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }
}
