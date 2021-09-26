package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ISalvagable extends IRarity {

    enum SalvageContext {
        SALVAGE_STATION

    }

    List<ItemStack> getSalvageResult(float salvageBonus);

    boolean isSalvagable(SalvageContext context);

    static ISalvagable load(ItemStack stack) {

        for (ItemstackDataSaver<? extends ISalvagable> saver : StackSaving.ALL_SALVAGABLE) {
            ISalvagable data = saver.loadFrom(stack);
            if (data != null) {
                return data;
            }
        }
        return null;
    }
}
