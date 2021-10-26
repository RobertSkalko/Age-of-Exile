package com.robertx22.age_of_exile.database.data.stats.types.crafting;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class RandomCraftingStat extends Stat implements DoNotTransferToCraftedMarker {

    public static RandomCraftingStat getInstance() {
        return RandomCraftingStat.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    public static String GUID = "random_craft_stat";

    private RandomCraftingStat() {

        this.min = -100;
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
        return "Random Stat";
    }

    private static class SingletonHolder {
        private static final RandomCraftingStat INSTANCE = new RandomCraftingStat();
    }
}
