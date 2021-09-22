package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.player_skills.ingredient.CraftedConsumableData;
import com.robertx22.age_of_exile.player_skills.ingredient.CraftingProcessData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class GearSoulOnInvTick {

    public static void checkAndGenerate(PlayerEntity player) {

        try {

            if (player.level.isClientSide) {
                return;
            }

            for (ItemStack stack : player.inventory.items) {

                if (stack.isEmpty()) {
                    continue;
                }

                if (StackSaving.STAT_SOULS.has(stack)) {
                    StatSoulData soul = StackSaving.STAT_SOULS.loadFrom(stack);

                    if (soul != null && soul.canInsertIntoStack(stack)) {
                        try {
                            GearItemData gear = soul.createGearData();
                            stack.removeTagKey(StatSoulItem.TAG);
                            gear.saveToStack(stack);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (StackSaving.CRAFT_PROCESS.has(stack)) {
                    try {
                        CraftingProcessData pdata = StackSaving.CRAFT_PROCESS.loadFrom(stack);
                        if (RandomUtils.roll(pdata.getSuccessChance(player))) {
                            CraftedConsumableData data = pdata.craft(player);
                            stack.setTag(new CompoundNBT()); // clear the craft process nbt
                            StackSaving.CRAFTED_CONSUMABLE.saveTo(stack, data);
                        } else {
                            stack.shrink(1);
                            PlayerUtils.giveItem(new ItemStack(Items.GUNPOWDER), player); // todo give specific fail items for each profession?
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
