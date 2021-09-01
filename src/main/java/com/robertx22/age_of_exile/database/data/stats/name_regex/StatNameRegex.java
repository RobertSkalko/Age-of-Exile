package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.util.Formatting;

public abstract class StatNameRegex {

    public static StatNameRegex BASIC = new BasicStatRegex();
    public static StatNameRegex BASIC_LOCAL = new BasicLocalStatRegex().setAddPlus(false);
    public static StatNameRegex JUST_NAME = new JustNameRegex();

    public static String VALUE = "VALUE";

    public static String MIN_VALUE = "MIN_VALUE";
    public static String MAX_VALUE = "MAX_VALUE";

    public static String NAME = "STAT_NAME";

    public boolean addPlus = true;

    protected StatNameRegex setAddPlus(boolean bool) {
        this.addPlus = bool;
        return this;
    }

    public Formatting statColor(Stat stat) {
        return Formatting.GRAY;
    }

    public Formatting numberColor(Formatting format, Stat stat, float val) {

        if (format != null) {
            return format;
        }

        if (val > 0) {
            return Formatting.GREEN;
        } else {
            return Formatting.RED;
        }
    }

    public abstract String getStatNameRegex(Formatting format, ModType type, Stat stat, float v1);

    public String translate(Formatting format, TooltipStatWithContext ctx, ModType type, float v1, Stat stat) {

        String v1s = NumberUtils.formatForTooltip(v1);

        if (stat.is_long) {
            String txt = CLOC.translate(stat.locName());

            txt = txt.replace(Stat.VAL1, v1s);

            return txt;
        }

        String percent = "";

        String plusminus = v1 > 0 && addPlus ? "+" : "";

        if (type == ModType.PERCENT || type == ModType.GLOBAL_INCREASE || stat.IsPercent()) {
            percent = "%";
        }

        String str = statColor(stat) + getStatNameRegex(format, type, stat, v1);

        str = str.replace(VALUE, numberColor(format, stat, v1) + "" + plusminus + v1s + percent + Formatting.RESET + statColor(stat));

        str = str.replace(NAME, CLOC.translate(stat.locName()));

        return str;

    }
}
