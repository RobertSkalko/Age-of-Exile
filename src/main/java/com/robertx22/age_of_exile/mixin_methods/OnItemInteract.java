package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class OnItemInteract {

    public static void on(ScreenHandler screen, int i, int j, SlotActionType slotActionType, PlayerEntity player, CallbackInfoReturnable<ItemStack> ci) {

        if (slotActionType != SlotActionType.PICKUP) {
            return;
        }

        ItemStack cursor = player.inventory.getCursorStack();

        if (!cursor.isEmpty()) {

            Slot slot = null;
            try {
                slot = screen.slots.get(i);
            } catch (Exception e) {
            }
            if (slot == null) {
                return;
            }

            ItemStack stack = slot.getStack();

            boolean success = false;

            if (stack.isDamaged() && cursor.getItem() instanceof SalvagedDustItem) {

                GearItemData gear = Gear.Load(stack);

                if (gear == null) {
                    return;
                }

                SalvagedDustItem essence = (SalvagedDustItem) cursor.getItem();

                if (essence.tier.getDisplayTierNumber() == gear.getTier()) {
                    stack.setDamage(stack.getDamage() - 250);
                    success = true;
                }
            } else if (cursor.getItem() instanceof StatSoulItem) {
                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(cursor);

                if (data != null) {
                    if (data.canInsertIntoStack(stack)) {
                        data.insertAsUnidentifiedOn(stack);
                        success = true;
                    }
                }
            } else if (cursor.getItem() instanceof ICurrencyItemEffect) {
                GearItemData gear = Gear.Load(stack);
                if (gear == null) {
                    return;
                }
                LocReqContext ctx = new LocReqContext(stack, cursor);
                if (ctx.effect.canItemBeModified(ctx)) {
                    ItemStack result = ctx.effect.modifyItem(ctx).stack;

                    stack.decrement(1);
                    PlayerUtils.giveItem(result, player);

                    success = true;
                }
            }

            if (success) {

                SoundUtils.ding(player.world, player.getBlockPos());
                SoundUtils.playSound(player.world, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_USE, 1, 1);

                cursor.decrement(1);
                ci.setReturnValue(ItemStack.EMPTY);
                ci.cancel();
            }

        }
    }
}
