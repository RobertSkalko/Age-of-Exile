package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.util.text.TextFormatting;

public class TierColors {

    public static TextFormatting get(int tier) {

        if (tier == 0) {
            return TextFormatting.GREEN;
        }
        if (tier == 1) {
            return TextFormatting.BLUE;
        }
        if (tier == 2) {
            return TextFormatting.LIGHT_PURPLE;
        }
        if (tier == 3) {
            return TextFormatting.DARK_PURPLE;
        }
        if (tier > 3) {
            return TextFormatting.DARK_PURPLE;
        }

        return TextFormatting.GRAY;
    }

}
