package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// this exists in 1.17 ItemStack and Item code. Remove when porting
@Mixin(ScreenHandler.class)
public abstract class ItemInteractionHook {

    @Inject(method = "method_30010(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;", cancellable = true, at = @At(value = "HEAD"))
    public void method_30010(int i, int j, SlotActionType slotActionType, PlayerEntity player, CallbackInfoReturnable<ItemStack> ci) {
        ScreenHandler screen = (ScreenHandler) (Object) this;
        OnItemInteract.on(screen, i, j, slotActionType, player, ci);
    }
}
