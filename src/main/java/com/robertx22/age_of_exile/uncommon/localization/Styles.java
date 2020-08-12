package com.robertx22.age_of_exile.uncommon.localization;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class Styles {

    public static Formatting BLUE = Formatting.BLUE;
    public static Formatting GOLD = Formatting.GOLD;
    public static Formatting GREEN = Formatting.GREEN;
    public static Formatting YELLOW = Formatting.YELLOW;
    public static Formatting RED = Formatting.RED;
    public static Formatting DARK_GREEN = Formatting.DARK_GREEN;
    public static Formatting GRAY = Formatting.GRAY;
    public static Formatting LIGHT_PURPLE = Formatting.LIGHT_PURPLE;
    public static Formatting AQUA = Formatting.AQUA;

    public static MutableText BLUECOMP() {
        return new LiteralText(BLUE + "");
    }

    public static MutableText GOLDCOMP() {
        return new LiteralText(GOLD + "");
    }

    public static MutableText GREENCOMP() {
        return new LiteralText(GREEN + "");
    }

    public static MutableText YELLOWCOMP() {
        return new LiteralText(YELLOW + "");
    }

    public static MutableText REDCOMP() {
        return new LiteralText(RED + "");
    }

    public static MutableText DARK_GREENCOMP() {
        return new LiteralText(DARK_GREEN + "");
    }

    public static MutableText GRAYCOMP() {
        return new LiteralText(GRAY + "");
    }

    public static MutableText LIGHT_PURPLECOMP() {
        return new LiteralText(LIGHT_PURPLE + "");
    }

    public static MutableText AQUACOMP() {
        return new LiteralText(AQUA + "");
    }

}


