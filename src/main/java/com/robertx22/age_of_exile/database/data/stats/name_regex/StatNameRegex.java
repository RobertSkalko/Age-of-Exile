package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.util.Formatting;

public abstract class StatNameRegex {

    public static StatNameRegex BASIC = new BasicStatRegex();
    public static StatNameRegex BASIC_LOCAL = new BasicLocalStatRegex();
    public static StatNameRegex REDUCED_REQ_BY_PECRENT = new ReducedReqByPercentRegex();
    public static StatNameRegex VALUE_PLUS_NAME = new ValuePlusName();
    public static StatNameRegex JUST_NAME = new JustNameRegex();

    public static String VALUE = "VALUE";

    public static String MIN_VALUE = "MIN_VALUE";
    public static String MAX_VALUE = "MAX_VALUE";

    public static String NAME = "STAT_NAME";

    static Formatting TEXT_COLOR = Formatting.GRAY;
    static Formatting NUMBER_COLOR = Formatting.GREEN;

    public abstract String getStatNameRegex(ModType type, Stat stat, float v1, float v2);

    public String translate(ModType type, float v1, float v2, Stat stat) {

        String v1s = NumberUtils.formatForTooltip(v1);
        String v2s = NumberUtils.formatForTooltip(v2);

        String percent = "";

        String plusminus = v1 > 0 ? "+" : "";

        if (!stat.add$plusminus$toTooltip) {
            plusminus = "";
            // delete the minuses
            v1s = v1s.replace("-", "");
            v2s = v2s.replace("-", "");
        }

        if (stat.UsesSecondValue()) {
            plusminus = "";
        }

        if (type == ModType.LOCAL_INCREASE || type == ModType.GLOBAL_INCREASE || stat.IsPercent()) {
            percent = "%";
        }

        String str = TEXT_COLOR + getStatNameRegex(type, stat, v1, v2);

        if (type == ModType.FLAT && stat.UsesSecondValue()) {
            str = str.replace(MIN_VALUE, NUMBER_COLOR + plusminus + v1s + percent + TEXT_COLOR);
            str = str.replace(MAX_VALUE, NUMBER_COLOR + plusminus + v2s + percent + TEXT_COLOR);
        } else {
            str = str.replace(VALUE, NUMBER_COLOR + "" + plusminus + v1s + percent + Formatting.RESET + TEXT_COLOR);
        }

        str = str.replace(NAME, CLOC.translate(stat.locName()));

        return str;

    }
}
