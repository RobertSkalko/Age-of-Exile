package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class RegeneratePercentStat extends Stat {

    public static RegeneratePercentStat HEALTH = new RegeneratePercentStat(Health.getInstance());
    public static RegeneratePercentStat MANA = new RegeneratePercentStat(Mana.getInstance());

    Stat statRestored;

    private RegeneratePercentStat(Stat statRestored) {
        this.statRestored = statRestored;
        this.scaling = StatScaling.NONE;

        this.statGroup = StatGroup.RESTORATION;

    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Restore % of your total per regen tick.";
    }

    @Override
    public String locNameForLangFile() {
        return statRestored.locNameForLangFile() + " Per Regen";
    }

    @Override
    public String GUID() {
        return statRestored.GUID() + "_per_sec";
    }

}
