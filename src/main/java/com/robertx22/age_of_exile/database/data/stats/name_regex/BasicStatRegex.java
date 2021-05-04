package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class BasicStatRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {

        if (stat.is_long) {
            return NAME;
        }

        if (type == ModType.FLAT) {

            String adds = "";

            String to = " ";

            if (stat.UsesSecondValue()) {
                return adds + MIN_VALUE + "-" + MAX_VALUE + " " + NAME;
            } else {
                return adds + VALUE + to + NAME;
            }
        }
        if (type == ModType.LOCAL_INCREASE) {
            String s = v1 > 0 && stat.IsPercent() ? " Extra " : " ";
            return VALUE + s + NAME;
        }
        if (type == ModType.GLOBAL_INCREASE) {
            return VALUE + " Total " + NAME;
        }

        return null;

    }
}
