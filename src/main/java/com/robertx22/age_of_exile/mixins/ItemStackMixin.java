package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemStoppedUsingCastImbuedSpell;
import com.robertx22.age_of_exile.mixin_methods.TooltipMethod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemStack.class, priority = Integer.MAX_VALUE)
public abstract class ItemStackMixin {
    public ItemStackMixin() {
    }

    // copied from TooltipCallback fabric event
    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")})
    private void getTooltip(PlayerEntity entity, ITooltipFlag tooltipContext, CallbackInfoReturnable<List<ITextComponent>> list) {
        ItemStack stack = (ItemStack) (Object) this;
        TooltipMethod.getTooltip(stack, entity, tooltipContext, list);
    }

    @Inject(method = {"releaseUsing"}, cancellable = true, at = {@At("HEAD")})
    public void onStoppedUsing(World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        OnItemStoppedUsingCastImbuedSpell.onStoppedUsing(stack, world, user, remainingUseTicks, ci);
    }

}
