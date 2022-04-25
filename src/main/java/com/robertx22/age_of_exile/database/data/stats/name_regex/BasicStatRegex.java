package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.text.TextFormatting;

public class BasicStatRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(TextFormatting format, ModType type, Stat stat, float v1) {

        if (stat.is_long) {
            return NAME;
        }

        if (type == ModType.FLAT) {
            return NAME + ": " + VALUE;

        }
        if (type == ModType.PERCENT) {
            String s = v1 > 0 && stat.IsPercent() ? "Extra " : "";
            return s + NAME + ": " + VALUE;
        }
        if (type == ModType.GLOBAL_INCREASE) {
            return "Total " + NAME + ": " + VALUE;
        }

        return null;

    }
}
