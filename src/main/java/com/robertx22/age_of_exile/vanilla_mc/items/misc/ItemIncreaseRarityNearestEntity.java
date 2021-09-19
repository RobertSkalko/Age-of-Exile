package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.phys.AABB;

public class ItemIncreaseRarityNearestEntity extends Item {

    public ItemIncreaseRarityNearestEntity() {

        super(new Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(64));

    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player,
                                       Hand hand) {

        if (!world.isClientSide) {
            try {

                AABB box = new AABB(player.blockPosition()).inflate(2);

                for (LivingEntity en : world.getEntitiesOfClass(LivingEntity.class, box)) {

                    if (en.is(player) == false && en instanceof PlayerEntity == false) {

                        EntityData data = Load.Unit(en);

                        if (data.increaseRarity()) {

                            player.getItemInHand(hand)
                                .shrink(1);

                            data.trySync();

                            return new ActionResult<ItemStack>(ActionResultType.PASS, player
                                .getItemInHand(hand));
                        } else {
                            player.displayClientMessage(Chats.No_targets_found.locName(), false);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }

}