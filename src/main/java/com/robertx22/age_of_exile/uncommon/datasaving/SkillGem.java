package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class SkillGem {

    private static final String LOC = "skill_gem_data";

    public static boolean has(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
            .contains(LOC);
    }

    public static SkillGemData Load(ItemStack stack) {

        if (stack == null) {
            return null;
        }
        if (!stack.hasTag()) {
            return null;
        }

        return LoadSave.Load(SkillGemData.class, new SkillGemData(), stack.getTag(), LOC);

    }

    public static void Save(ItemStack stack, SkillGemData gem) {

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
