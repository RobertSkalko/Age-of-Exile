package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.database.data.spells.components.ImbueType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class OnItemStoppedUsingCastImbuedSpell {

    public static void register() {

        ForgeEvents.registerForgeEvent(LivingEntityUseItemEvent.Stop.class, event -> {
            if (!event.getEntityLiving().level.isClientSide) {
                boolean used = onStoppedUsingBow(event.getEntityLiving());
                if (used) {
                    event.setCanceled(true);
                }
            }
        });

        ForgeEvents.registerForgeEvent(CriticalHitEvent.class, event -> {

            if (event.isVanillaCritical()) {
                if (!event.getPlayer().level.isClientSide) {
                    Load.spells(event.getPlayer())
                        .getCastingData()
                        .tryCastImbuedSpell(event.getPlayer(), ImbueType.ON_CRIT);
                }
            }
        });
    }

    public static boolean isCorrectRangedAttackFinish(LivingEntity user) {

        ItemStack stack = user.getUseItem();

        int remainingUseTicks = user.getUseItemRemainingTicks();

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

        return shoot;

    }

    public static boolean onStoppedUsingBow(LivingEntity user) {
        if (isCorrectRangedAttackFinish(user)) {
            return Load.spells(user)
                .getCastingData()
                .tryCastImbuedSpell(user, ImbueType.ON_RANGED_ATTACK);
        }
        return false;
    }

    public static void crossbow(ItemStack stack, World world, PlayerEntity user, Hand
        hand, CallbackInfoReturnable<ActionResult<ItemStack>> ci) {

        try {
            if (user.level.isClientSide) {
                return;
            }
            if (stack.getItem() instanceof CrossbowItem) {
                if (CrossbowItem.isCharged(stack)) {
                    if (Load.spells(user)
                        .getCastingData()
                        .tryCastImbuedSpell(user, ImbueType.ON_RANGED_ATTACK)) {
                        //ci.cancel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
