package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

public class BasicLocalStatRegex extends StatNameRegex {

    @Override
    public Formatting statColor(Stat stat) {
        return Formatting.WHITE;
    }

    @Override
    public Formatting numberColor(Formatting format, Stat stat, float val) {
        return stat.getFormat();
    }

    @Override
    public String getStatNameRegex(Formatting format, ModType type, Stat stat, float v1) {

        return NAME + ": " + numberColor(format, stat, v1) + VALUE;

    }
}
