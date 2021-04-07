package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

public class AffixStatsWithIcon extends StatNameRegex {

    @Override
    public Formatting statColor(Stat stat) {
        return stat.textFormat;
    }

    @Override
    public Formatting numberColor(Stat stat) {
        return Formatting.GREEN;
    }

    @Override
    public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {

        String space = stat.textIcon.isEmpty() ? "" : " ";

        if (type == ModType.FLAT) {

            String adds = "";

            String to = " ";

            if (stat.UsesSecondValue()) {
                return numberColor(stat) + adds + MIN_VALUE + numberColor(stat) + "-" + MAX_VALUE + " " + stat.textIcon + space + NAME;
            } else {
                return numberColor(stat) + adds + VALUE + to + stat.textIcon + space + Formatting.GRAY + NAME;
            }
        }
        if (type == ModType.LOCAL_INCREASE) {
            String s = v1 > 0 ? " Extra " : " ";
            return VALUE + space + stat.textIcon + s + NAME;
        }
        if (type == ModType.GLOBAL_INCREASE) {
            return VALUE + " Total " + stat.textIcon + space + NAME;
        }

        return "";
    }

}

