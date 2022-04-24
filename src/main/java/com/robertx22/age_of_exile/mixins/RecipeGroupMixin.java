package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.vanilla_mc.blocks.MNSCraftingTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(RecipeBookCategories.class)
public class RecipeGroupMixin {

    @Inject(method = "getIconItems", cancellable = true, at = @At(value = "HEAD"))
    private void ssddsdd2525(CallbackInfoReturnable<List<ItemStack>> cir) {

        if (!MNSCraftingTableBlock.isPlayerUsing(Minecraft.getInstance().player)) {
            return;
        }
        RecipeBookCategories group = (RecipeBookCategories) (Object) this;

        if (group == RecipeBookCategories.CRAFTING_EQUIPMENT) {
            cir.setReturnValue(Arrays.asList(SlashItems.RUNEWORD.get()
                .getDefaultInstance()));
        }
    }
}
