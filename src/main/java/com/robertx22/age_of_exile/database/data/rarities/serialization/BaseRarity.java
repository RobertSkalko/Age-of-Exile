package com.robertx22.age_of_exile.database.data.rarities.serialization;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.Formatting;

public class BaseRarity implements Rarity {

    public BaseRarity() {

    }

    public int rank;
    public int weight;
    public Formatting text_format;
    public String loc_name_id;
    public String loc_name;
    public String guid;

    @Override
    public String locNameLangFileGUID() {
        return loc_name_id;
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
        return text_format;
    }

    @Override
    public String locNameForLangFile() {
        return loc_name;
    }

    protected void setCommonFields() {
        this.guid = "common";
        this.loc_name = "Common";
        this.text_format = Formatting.GRAY;
        this.rank = IRarity.Common;
    }

    protected void setMagicalFields() {
        this.guid = "magical";
        this.loc_name = "Magical";
        this.text_format = Formatting.GREEN;
        this.rank = IRarity.Magical;
    }

    protected void setRareFields() {
        this.guid = "rare";
        this.loc_name = "Rare";
        this.text_format = Formatting.YELLOW;
        this.rank = IRarity.Rare;
    }

    protected void setEpicFields() {
        this.guid = "epic";
        this.loc_name = "Epic";
        this.text_format = Formatting.LIGHT_PURPLE;
        this.rank = IRarity.Epic;
    }

    protected void setLegendaryFields() {
        this.guid = "legendary";
        this.loc_name = "Legendary";
        this.text_format = Formatting.GOLD;
        this.rank = IRarity.Legendary;
    }

    protected void setRelicFields() {
        this.guid = "relic";
        this.loc_name = "Relic";
        this.text_format = Formatting.GOLD;
        this.rank = IRarity.Relic;
    }

    protected void setUniqueFields() {
        this.guid = "unique";
        this.loc_name = "Unique";
        this.text_format = Formatting.RED;
        this.rank = IRarity.Unique;
    }

}

