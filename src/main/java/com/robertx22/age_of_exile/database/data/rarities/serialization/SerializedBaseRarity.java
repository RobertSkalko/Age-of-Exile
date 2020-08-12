package com.robertx22.age_of_exile.database.data.rarities.serialization;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.util.Formatting;

public class SerializedBaseRarity implements Rarity {

    public SerializedBaseRarity(SerializedBaseRarity o) {

        this.rank = o.rank;
        this.weight = o.weight;
        this.textFormatting = o.textFormatting;
        this.locNameID = o.locNameID;
        this.locName = o.locName;
        this.guid = o.guid;
    }

    public SerializedBaseRarity() {

    }

    public int rank;
    public int weight;
    public Formatting textFormatting;
    public String locNameID;
    public String locName;
    public String guid;

    @Override
    public String locNameLangFileGUID() {
        return locNameID;
    }

    @Override
    public String GUID() {
        return guid;
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

