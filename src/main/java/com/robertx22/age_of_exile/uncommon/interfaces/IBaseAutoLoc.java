package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.database.data.IGUID;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

public interface IBaseAutoLoc extends IGUID {

    enum AutoLocGroup {
        Runes,
        Unique_Items,
        Spells,
        Gear_Items,
        Words,
        Rarities,
        Affixes,

        Rune_Words,
        Item_Sets,
        Stats,
        Misc,
        Gear_Slots,
        World_Types,
        Lootboxes,
        Chat_Messages,
        Configs,
        Currency_Items,
        Advancement_titles,
        Advancement_descriptions,
        Potions,
        Alchemy,

    }

    default String getPrefix() {
        if (this instanceof Item) {
            return "item.";
        } else if (this instanceof Block) {
            return "block.";
        } else if (this instanceof StatusEffect) {
            return "effect.";
        } else {
            return "";
        }

    }

}
