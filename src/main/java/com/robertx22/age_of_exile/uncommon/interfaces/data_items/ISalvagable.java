package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ISalvagable extends IRarity {

    enum SalvageContext {
        SALVAGE_STATION

    }

    List<ItemStack> getSalvageResult(float salvageBonus);

    boolean isSalvagable(SalvageContext context);

    default int tryIncreaseAmount(float salvageBonus, int amount) {

        if (RandomUtils.roll(salvageBonus)) {
            return amount + 1;
        }

        return amount;
    }
}
