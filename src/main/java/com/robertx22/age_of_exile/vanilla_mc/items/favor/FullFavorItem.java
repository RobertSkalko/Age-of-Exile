package com.robertx22.age_of_exile.vanilla_mc.items.favor;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FullFavorItem extends Item {

    public static int FAVOR_CAPACITY = 1000;

    public FullFavorItem() {

        super(new Item.Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(64));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player,
                                       Hand hand) {

        if (!world.isClientSide) {
            try {
                ItemStack stack = player.getItemInHand(hand);

                stack.shrink(1);

                Load.playerRPGData(player).favor
                    .setFavor(Load.playerRPGData(player).favor
                        .getFavor() + FAVOR_CAPACITY);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }

}
