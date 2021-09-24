package com.robertx22.age_of_exile.database.data.stats.types.crafting;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class CraftingSuccessChance extends Stat implements DoNotTransferToCraftedMarker {

    public static CraftingSuccessChance getInstance() {
        return CraftingSuccessChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "r";
    }

    public static String GUID = "craft_suc_chance";

    private CraftingSuccessChance() {

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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Success Chance";
    }

    private static class SingletonHolder {
        private static final CraftingSuccessChance INSTANCE = new CraftingSuccessChance();
    }
}
