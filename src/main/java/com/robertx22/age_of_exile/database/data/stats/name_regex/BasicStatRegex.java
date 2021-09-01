package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class BasicStatRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ModType type, Stat stat, float v1) {

        if (stat.is_long) {
            return NAME;
        }

        if (type == ModType.FLAT) {

            String adds = "";

            String to = " ";

            return adds + VALUE + to + NAME;

        }
        if (type == ModType.PERCENT) {
            String s = v1 > 0 && stat.IsPercent() ? " Extra " : " ";
            return VALUE + s + NAME;
        }
        if (type == ModType.GLOBAL_INCREASE) {
            return VALUE + " Total " + NAME;
        }

        return null;

    }
}
