package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilterGroup;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public enum ItemType {

    GEAR(ItemFilterGroup.ANY_GEAR, Words.Gears),
    SPELL_GEM(ItemFilterGroup.ANY_SPELL_GEM, Words.Gears),
    DUNGEON_KEY(ItemFilterGroup.DUNGEON_KEY, Words.DungeonKey),
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

    public static MutableText getTooltipString(ItemType types) {

        MutableText comp = Words.UsableOn.locName()
            .append(": ")
            .append(types.word.locName());

        comp.formatted(Formatting.LIGHT_PURPLE);

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
