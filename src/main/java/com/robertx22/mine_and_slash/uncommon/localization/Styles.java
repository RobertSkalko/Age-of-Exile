package com.robertx22.mine_and_slash.uncommon.localization;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
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

    public static Text BLUECOMP() {
        return new LiteralText(BLUE + "");
    }

    public static Text GOLDCOMP() {
        return new LiteralText(GOLD + "");
    }

    public static Text GREENCOMP() {
        return new LiteralText(GREEN + "");
    }

    public static Text YELLOWCOMP() {
        return new LiteralText(YELLOW + "");
    }

    public static Text REDCOMP() {
        return new LiteralText(RED + "");
    }

    public static Text DARK_GREENCOMP() {
        return new LiteralText(DARK_GREEN + "");
    }

    public static Text GRAYCOMP() {
        return new LiteralText(GRAY + "");
    }

    public static Text LIGHT_PURPLECOMP() {
        return new LiteralText(LIGHT_PURPLE + "");
    }

    public static Text AQUACOMP() {
        return new LiteralText(AQUA + "");
    }

}


