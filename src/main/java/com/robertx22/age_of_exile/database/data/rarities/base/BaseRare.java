package com.robertx22.age_of_exile.database.data.rarities.base;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.Formatting;

public abstract class BaseRare implements Rarity {

    @Override
    public String GUID() {

        return "rare";
    }

    @Override
    public int Rank() {

        return IRarity.Rare;
    }

    @Override
    public Formatting textFormatting() {
        return Formatting.YELLOW;
    }

    @Override
    public String locNameForLangFile() {
        return "Rare";
    }
}
