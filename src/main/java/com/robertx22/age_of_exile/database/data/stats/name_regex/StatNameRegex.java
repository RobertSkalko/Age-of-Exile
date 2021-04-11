package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.util.Formatting;

public abstract class StatNameRegex {

    public static StatNameRegex BASIC = new BasicStatRegex();
    public static StatNameRegex BASIC_LOCAL = new BasicLocalStatRegex().setAddPlus(false);
    public static StatNameRegex VALUE_PLUS_NAME = new ValuePlusName();
    public static StatNameRegex JUST_NAME = new JustNameRegex();
    public static StatNameRegex STATS_WITH_ICON = new AffixStatsWithIcon();

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

    public Formatting numberColor(Stat stat) {
        return Formatting.GREEN;
    }

    public abstract String getStatNameRegex(ModType type, Stat stat, float v1, float v2);

    public String translate(TooltipStatWithContext ctx, ModType type, float v1, float v2, Stat stat) {

        String v1s = NumberUtils.formatForTooltip(v1);
        String v2s = NumberUtils.formatForTooltip(v2);

        if (stat.isLongStat) {
            String txt = CLOC.translate(stat.locName());

            txt = txt.replace(SpecialStats.VAL1, v1s);
            txt = txt.replace(SpecialStats.VAL2, v2s);

            return txt;
        }

        String percent = "";

        String plusminus = v1 > 0 && addPlus ? "+" : "";

        if (!stat.add$plusminus$toTooltip) {
            plusminus = "";
            // delete the minuses
            v1s = v1s.replace("-", "");
            v2s = v2s.replace("-", "");
        }

        if (type == ModType.LOCAL_INCREASE || type == ModType.GLOBAL_INCREASE || stat.IsPercent()) {
            percent = "%";
        }

        String str = statColor(stat) + getStatNameRegex(type, stat, v1, v2);

        if (type == ModType.FLAT && stat.UsesSecondValue()) {
            str = str.replace(MIN_VALUE, numberColor(stat) + plusminus + v1s + percent + statColor(stat));
            str = str.replace(MAX_VALUE, numberColor(stat) + v2s + percent + statColor(stat));
        } else {
            str = str.replace(VALUE, numberColor(stat) + "" + plusminus + v1s + percent + Formatting.RESET + statColor(stat));
        }

        str = str.replace(NAME, CLOC.translate(stat.locName()));

        return str;

    }
}
