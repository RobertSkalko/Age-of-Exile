package com.robertx22.age_of_exile.mixins.bow;

import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.BowWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileUtil.class)
public abstract class ProjectileUtilMixin {

    private static final Hand[] HANDS = {
        Hand.MAIN_HAND,
        Hand.OFF_HAND
    }; // Cache the hands to not create the hands array each time the loop is run

    // Because the uses of this method are hardcoded, checking each hand for the Fabric interfaces of the items is needed.
    // Note: this does not cancel for the vanilla items unless they are holding a custom implementation of the items
    @Inject(method = "getHandPossiblyHolding", at = @At(value = "HEAD"), cancellable = true)
    private static void getHandPossiblyHolding(LivingEntity entity, Item item, CallbackInfoReturnable<Hand> cir) {
        for (Hand hand : HANDS) {
            if (item == Items.BOW) { // Make sure we only check for bows when searching for bows and allow for other items like crossbows in future
                if (entity.getStackInHand(hand)
                    .getItem() instanceof BowWeapon) {
                    cir.setReturnValue(hand);
                    return;
                }
            }
        }
    }
}