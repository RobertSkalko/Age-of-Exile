package com.robertx22.age_of_exile.vanilla_mc.blocks.slots;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilterGroup;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class AnyItemNotACurrencyEffectSlot extends Slot {
    public AnyItemNotACurrencyEffectSlot(Inventory inventoryIn, int index, int xPosition,
                                         int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return SkillGemData.fromStack(stack) != null || !ItemFilterGroup.ANY_CURRENCY_EFFECT.anyMatchesFilter(stack);
    }
}

