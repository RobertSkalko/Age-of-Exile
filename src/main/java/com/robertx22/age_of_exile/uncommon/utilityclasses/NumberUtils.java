package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;

import java.text.DecimalFormat;

public class NumberUtils {
    public static int multiToPercent(float multi) {

        multi = 1F - multi;

        return (int) (multi * 100F);

    }

    private static String format(int number, int divided, String letter) {
        int amount = number / divided;
        int remaining = (number - (amount * divided));

        if (remaining > 0) {
            remaining /= (divided / 10);
        }

        String firstRemaining = (remaining + "").substring(0, 1);

        return amount + "." + firstRemaining + letter;
    }

    static int MILLS = 1000000;
    static int THOUSANDS = 1000;

    public static String format(float number) {
        return format((int) number);
    }

    static DecimalFormat format = new DecimalFormat();

    static {
        format = new DecimalFormat();
        format.setMaximumFractionDigits(1);
    }

    public static String formatForTooltip(float num) {
        if (Math.abs(num) < 10) {
            return format.format(num);
        } else {
            return (int) num + "";
        }
    }

    public static String format(int number) {

        if (Math.abs(number / MILLS) >= 1) {
            return format(number, MILLS, "m");
        } else if (Math.abs(number / THOUSANDS) >= 1) {
            return format(number, THOUSANDS, "k");
        } else {
            return number + "";
        }

    }

    public static String formatDamageNumber(DamageEffect data, int val) {
        String num = format(val);

        if (data.data.getBoolean(EventData.CRIT)) {
            num += "!";
        }

        return num;
    }

}
