package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// this exists in 1.17 ItemStack and Item code. Remove when porting
@Mixin(Container.class)
public abstract class ItemInteractionHook {

    @Inject(method = "doClick(IILnet/minecraft/inventory/container/ClickType;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;", cancellable = true, at = @At(value = "HEAD"))
    public void method_30010(int i, int j, ClickType slotActionType, PlayerEntity player, CallbackInfoReturnable<ItemStack> ci) {
        Container screen = (Container) (Object) this;
        OnItemInteract.on(screen, i, j, slotActionType, player, ci);
    }
}
