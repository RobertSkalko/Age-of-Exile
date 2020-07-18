package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilterGroup;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

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

    public static Text getTooltipString(ItemType types) {

        Text comp = Styles.LIGHT_PURPLECOMP()
            .append(Words.UsableOn.locName())
            .append(": ")
            .append(types.word.locName());

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
