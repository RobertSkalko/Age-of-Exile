package com.robertx22.age_of_exile.database.data.stats.types.resources.health;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class Health extends Stat implements IUsableStat {
    public static String GUID = "health";

    private Health() {
        this.min = 1;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.order = 0;
        this.icon = "\u2764";
        this.format = TextFormatting.RED.getName();

    }

    public static Health getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your total hearts amount.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Health";
    }

    @Override
    public float getMaxMulti() {
        return ServerContainer.get().MAX_ENVIRO_DMG_PROTECTION_FROM_HP_MULTI.get()
            .floatValue();
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return ServerContainer.get().HP_VALUE_NEEDED_FOR_MAX_ENVIRO_DMG_PROTECTION.get();
    }

    private static class SingletonHolder {
        private static final Health INSTANCE = new Health();
    }
}
