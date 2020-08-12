package com.robertx22.age_of_exile.database.data.rarities.base;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.Formatting;

public abstract class BaseUnique implements Rarity {
    @Override
    public String GUID() {

        return "unique";
    }

    @Override
    public int Rank() {

        return IRarity.Unique;
    }

    @Override
    public Formatting textFormatting() {
        return Formatting.RED;
    }

    @Override
    public int Weight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Unique";
    }

}