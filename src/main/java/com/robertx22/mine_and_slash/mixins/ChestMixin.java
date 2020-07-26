package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mixin_methods.GenChestLootMethod;
import net.minecraft.inventory.Inventory;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTable.class)
public abstract class ChestMixin {

    @Inject(method = "supplyInventory", at = @At(value = "TAIL"))
    public void onLootGen(Inventory inventory, LootContext context, CallbackInfo ci) {
        GenChestLootMethod.onLootGen(inventory, context, ci);
    }

}
