package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.text.TextFormatting;

public class BasicLocalStatRegex extends StatNameRegex {

    @Override
    public TextFormatting statColor(Stat stat) {
        return TextFormatting.WHITE;
    }

    @Override
    public TextFormatting numberColor(TextFormatting format, Stat stat, float val) {
        return stat.getFormat();
    }

    @Override
    public String getStatNameRegex(TextFormatting format, ModType type, Stat stat, float v1) {

        return NAME + ": " + numberColor(format, stat, v1) + VALUE;

    }
}
