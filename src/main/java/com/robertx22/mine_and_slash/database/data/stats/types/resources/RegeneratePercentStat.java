package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class RegeneratePercentStat extends Stat {

    public static RegeneratePercentStat HEALTH = new RegeneratePercentStat(Health.getInstance());
    public static RegeneratePercentStat MANA = new RegeneratePercentStat(Mana.getInstance());
    public static RegeneratePercentStat MAGIC_SHIELD = new RegeneratePercentStat(MagicShield.getInstance());

    Stat statRestored;

    private RegeneratePercentStat(Stat statRestored) {
        this.statRestored = statRestored;

    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.NONE;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return new StatNameRegex() {
            @Override
            public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {
                return "Restore " + StatNameRegex.VALUE + " " + StatNameRegex.NAME;
            }
        };
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
        return "Restore % of your total per second.";
    }

    @Override
    public String locNameForLangFile() {
        return statRestored.locNameForLangFile() + " Per Second";
    }

    @Override
    public String GUID() {
        return statRestored.GUID() + "_per_sec";
    }

}
