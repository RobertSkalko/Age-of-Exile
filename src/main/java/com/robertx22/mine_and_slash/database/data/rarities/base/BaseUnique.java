package com.robertx22.mine_and_slash.database.data.rarities.base;

import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
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