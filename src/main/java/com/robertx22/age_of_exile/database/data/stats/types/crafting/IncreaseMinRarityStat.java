package com.robertx22.age_of_exile.database.data.stats.types.crafting;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class IncreaseMinRarityStat extends Stat implements DoNotTransferToCraftedMarker {

    public static IncreaseMinRarityStat getInstance() {
        return IncreaseMinRarityStat.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases minimum rarity of the resulting crafted gear";
    }

    public static String GUID = "inc_min_rar_craft";

    private IncreaseMinRarityStat() {

        this.min = 0;
        this.scaling = StatScaling.NONE;
        this.group = StatGroup.Misc;
        this.icon = "\u2748";
        this.format = TextFormatting.BLUE.getName();

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Increased Min Rarity";
    }

    private static class SingletonHolder {
        private static final IncreaseMinRarityStat INSTANCE = new IncreaseMinRarityStat();
    }
}
