package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemUseCastSpell;
import com.robertx22.age_of_exile.mixin_methods.TooltipMethod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemStack.class, priority = Integer.MAX_VALUE)
public abstract class ItemStackMixin {
    public ItemStackMixin() {
    }

    // copied from TooltipCallback fabric event
    @Inject(method = {"getTooltip"}, at = {@At("RETURN")})
    private void getTooltip(PlayerEntity entity, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> list) {
        ItemStack stack = (ItemStack) (Object) this;
        TooltipMethod.getTooltip(stack, entity, tooltipContext, list);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onItemUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        ItemStack stack = (ItemStack) (Object) this;
        OnItemUseCastSpell.onItemUse(stack, world, user, hand, ci);
    }
}
