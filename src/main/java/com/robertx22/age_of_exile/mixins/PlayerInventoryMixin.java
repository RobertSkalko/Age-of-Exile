package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.salvage_bag.SalvageBagItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "insertStack(Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "HEAD"))
    private void salvageGear(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        try {
            PlayerInventory inv = (PlayerInventory) (Object) this;

            List<ItemStack> list = MyCurioUtils.getSalvageStack(inv.player);

            if (!list.isEmpty()) {

                ItemStack bagstack = list.get(0);
                if (bagstack.getItem() instanceof SalvageBagItem) {
                    SalvageBagItem bag = (SalvageBagItem) bagstack.getItem();

                    GearItemData gear = Gear.Load(stack);
                    if (gear != null) {
                        if (bag.shouldSalvage(inv.player, gear)) {

                            List<ItemStack> results = gear.getSalvageResult(0);

                            stack.decrement(1);

                            if (RandomUtils.roll(bag.chanceToDestroyOutput())) {
                                SoundUtils.playSound(inv.player, SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);
                                return;

                            } else {
                                SoundUtils.ding(inv.player.world, inv.player.getBlockPos());
                                SoundUtils.playSound(inv.player, SoundEvents.ENTITY_ITEM_PICKUP, 1, 1);
                                results.forEach(x -> PlayerUtils.giveItem(x, inv.player));
                            }

                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
