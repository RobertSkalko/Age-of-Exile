package com.robertx22.age_of_exile.vanilla_mc.items.favor;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EmptyFavorItem extends Item {

    public EmptyFavorItem() {

        super(new Item.Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player,
                                            Hand hand) {

        if (!world.isClient) {
            try {

                ItemStack stack = player.getStackInHand(hand);

                if (Load.Unit(player)
                    .getLevel() >= 20) {

                    RPGPlayerData rpg = Load.playerRPGData(player);

                    if (rpg.favor.getFavor() >= FullFavorItem.FAVOR_CAPACITY) {
                        rpg.favor.setFavor(rpg.favor.getFavor() - FullFavorItem.FAVOR_CAPACITY);

                        stack.decrement(1);

                        ItemStack newstack = new ItemStack(ModRegistry.MISC_ITEMS.FULL_FAVOR);
                        PlayerUtils.giveItem(newstack, player);
                    }
                } else {
                    player.sendMessage(new LiteralText("You must be lvl 20 or higher to use this item."), false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

}
