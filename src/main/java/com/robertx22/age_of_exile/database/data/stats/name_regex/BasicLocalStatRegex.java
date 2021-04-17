package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

public class BasicLocalStatRegex extends StatNameRegex {

    @Override
    public Formatting statColor(Stat stat) {
        return stat.textFormat;
    }

    @Override
    public Formatting numberColor(Stat stat, float val) {
        return stat.textFormat;
    }

    @Override
    public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {

        if (type.isFlat() && stat.UsesSecondValue()) {
            return NAME + ": " + numberColor(stat, v1) + MIN_VALUE + numberColor(stat, v2) + "-" + MAX_VALUE;
        } else {
            return NAME + ": " + numberColor(stat, v1) + VALUE;
        }

    }
}
