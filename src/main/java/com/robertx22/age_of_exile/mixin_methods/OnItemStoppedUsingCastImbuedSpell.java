package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class OnItemStoppedUsingCastImbuedSpell {
    public static void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {

        boolean shoot = false;
        int usedur = stack.getItem()
            .getUseDuration(stack) - remainingUseTicks;

        if (stack.getItem() instanceof BowItem && usedur > 20 * 1) {
            shoot = true;
        }
        if (stack.getItem() instanceof BowItem) {
            int i = stack.getItem()
                .getUseDuration(stack) - remainingUseTicks;
            float f = BowItem.getPowerForTime(i);
            if (f == 1F) {
                shoot = true;

            }
        }

        if (shoot) {
            if (Load.spells(user)
                .getCastingData()
                .tryCastImbuedSpell(user)) {
                ci.cancel();
            }
        }

    }

    public static void crossbow(ItemStack stack, World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> ci) {

        try {
            if (user.level.isClientSide) {
                return;
            }
            if (stack.getItem() instanceof CrossbowItem) {
                if (CrossbowItem.isCharged(stack)) {
                    if (Load.spells(user)
                        .getCastingData()
                        .tryCastImbuedSpell(user)) {
                        //ci.cancel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
