package com.robertx22.mine_and_slash.database.data.rarities.serialization;

import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import java.util.Locale;
import net.minecraft.util.Formatting;

public class SerializedBaseRarity implements Rarity {

    public SerializedBaseRarity(SerializedBaseRarity o) {

        this.rank = o.rank;
        this.weight = o.weight;
        this.textFormatting = o.textFormatting;
        this.locNameID = o.locNameID;
        this.locName = o.locName;
    }

    public SerializedBaseRarity() {

    }

    public int rank;
    public int weight;
    public Formatting textFormatting;
    public String locNameID;
    public String locName;

    @Override
    public String locNameLangFileGUID() {
        return locNameID;
    }

    @Override
    public String GUID() {
        return locNameForLangFile().toLowerCase(Locale.ROOT);
    }

    @Override
    public int Rank() {
        return rank;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Formatting textFormatting() {
        return textFormatting;
    }

    @Override
    public String locNameForLangFile() {
        return locName;
    }

}

