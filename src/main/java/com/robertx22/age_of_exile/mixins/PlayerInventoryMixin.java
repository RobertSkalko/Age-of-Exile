package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackContainer;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackInfo;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.TryAutoInsert;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {


    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(method = "add(Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "HEAD"))
    private void hookonStackInserted(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        try {
            PlayerInventory inv = (PlayerInventory) (Object) this;

            List<ItemStack> backpacks = new ArrayList<>();

            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack trystack = inv.getItem(i);

                if (trystack.getItem() instanceof BackpackItem) {
                    backpacks.add(trystack);
                }
            }

            if (!backpacks.isEmpty()) {

                for (ItemStack bagstack : backpacks) {

                    BackpackInfo info = BackpackContainer.getInfo(this.player, bagstack);

                    GearItemData gear = Gear.Load(stack);
                    if (gear != null) {

                        if (info.canSalvage(stack)) {

                            List<ItemStack> results = gear.getSalvageResult(0);

                            stack.shrink(1);

                            if (RandomUtils.roll(25)) {
                                SoundUtils.playSound(inv.player, SoundEvents.FIRE_EXTINGUISH, 1, 1);
                                return;

                            } else {
                                SoundUtils.ding(inv.player.level, inv.player.blockPosition());
                                SoundUtils.playSound(inv.player, SoundEvents.ITEM_PICKUP, 1, 1);
                                results.forEach(x -> PlayerUtils.giveItem(x, inv.player));
                            }

                        }

                    }
                }
            }

            if (!stack.isEmpty()) {
                TryAutoInsert.run(inv, stack);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
