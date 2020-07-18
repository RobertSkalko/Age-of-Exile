package com.robertx22.mine_and_slash.database.data.stats.name_regex;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class ReducedReqByPercentRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {
        if (v1 > 0) {
            return VALUE + " Reduced " + NAME;
        } else {
            return VALUE + " Increased " + NAME;
        }

    }
}
