package com.robertx22.age_of_exile.database.data.rarities.base;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.Formatting;

public abstract class BaseEpic implements Rarity {

    @Override
    public String GUID() {

        return "epic";
    }

    @Override
    public int Rank() {
        return IRarity.Epic;
    }

    @Override
    public Formatting textFormatting() {
        return Formatting.LIGHT_PURPLE;
    }

    @Override
    public String locNameForLangFile() {
        return "Epic";
    }
}