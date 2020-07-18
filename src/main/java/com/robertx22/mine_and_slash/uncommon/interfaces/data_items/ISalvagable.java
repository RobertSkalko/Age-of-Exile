package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;

public interface ISalvagable extends IRarity {

    enum SalvageContext {
        SALVAGE_STATION

    }

    ItemStack getSalvageResult(float salvageBonus);

    boolean isSalvagable(SalvageContext context);

    default int tryIncreaseAmount(float salvageBonus, int amount) {

        if (RandomUtils.roll(salvageBonus)) {
            return amount + 1;
        }

        return amount;
    }
}
