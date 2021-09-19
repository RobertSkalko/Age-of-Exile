package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilterGroup;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;

public enum ItemType {

    GEAR(ItemFilterGroup.ANY_GEAR, Words.Gears),
    NONE(null, Words.None);

    ItemType(ItemFilterGroup filter, Words word) {
        this.filter = filter;
        this.word = word;
    }

    public boolean isType(ItemStack stack) {
        if (filter == null) {
            return false;
        }
        return filter.anyMatchesFilter(stack);
    }

    private ItemFilterGroup filter;
    public Words word;

    public static IFormattableTextComponent getTooltipString(ItemType types) {

        IFormattableTextComponent comp = Words.UsableOn.locName()
            .append(": ")
            .append(types.word.locName());

        comp.withStyle(TextFormatting.LIGHT_PURPLE);

        return comp;

    }

    public static ItemType getType(ItemStack stack) {

        for (ItemType type : ItemType.values()) {
            if (type.isType(stack)) {
                return type;
            }
        }
        return NONE;
    }

}
