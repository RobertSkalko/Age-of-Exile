package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ItemIncreaseRarityNearestEntity extends Item {

    public ItemIncreaseRarityNearestEntity() {

        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player,
                                                    Hand hand) {

        if (!world.isClient) {
            try {

                Box box = new Box(player.getBlockPos()).expand(2);

                for (LivingEntity en : world.getNonSpectatingEntities(LivingEntity.class, box)) {

                    if (en.isPartOf(player) == false && en instanceof PlayerEntity == false) {

                        UnitData data = Load.Unit(en);

                        if (data.increaseRarity(en)) {

                            player.getStackInHand(hand)
                                .decrement(1);

                            data.trySync(en);

                            return new TypedActionResult<ItemStack>(ActionResult.PASS, player
                                .getStackInHand(hand));
                        } else {
                            player.sendMessage(Chats.No_targets_found.locName());
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

}