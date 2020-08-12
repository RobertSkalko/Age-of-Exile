package com.robertx22.age_of_exile.config.forge.parts;

import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import net.minecraft.item.Item;

public class AutoConfigItemType {

    public int MAX_LEVEL;

    public double POWER_REQ;

    public int MIN_RARITY;
    public int MAX_RARITY;

    public boolean CAN_BE_SALVAGED;

    public AutoConfigItemType(float req, int maxlvl, int minrar, int maxrar) {

        MAX_LEVEL = maxlvl;

        POWER_REQ = req;

        MIN_RARITY = minrar;

        MAX_RARITY = maxrar;

        CAN_BE_SALVAGED = false;

    }

    public CompatibleItem getAutoCompatibleItem(Item item, BaseGearType slot) {

        CompatibleItem comp = CompatibleItem.getDefaultAuto(item, slot);

        comp.max_rarity = MAX_RARITY;
        comp.min_rarity = MIN_RARITY;

        comp.can_be_salvaged = CAN_BE_SALVAGED;

        return comp;

    }
}
