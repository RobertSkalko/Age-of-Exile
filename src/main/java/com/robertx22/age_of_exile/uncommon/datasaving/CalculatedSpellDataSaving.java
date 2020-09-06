package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class CalculatedSpellDataSaving {

    private static final String LOC = "skill_gem_data";

    public static boolean has(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
            .contains(LOC);
    }

    public static CalculatedSpellData Load(ItemStack stack) {

        if (stack == null) {
            return null;
        }
        if (!stack.hasTag()) {
            return null;
        }

        return LoadSave.Load(CalculatedSpellData.class, new CalculatedSpellData(), stack.getTag(), LOC);

    }

    public static void Save(ItemStack stack, CalculatedSpellData gem) {

        if (stack == null) {
            return;
        }
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        if (gem != null) {
            LoadSave.Save(gem, stack.getTag(), LOC);
        }

    }

}
