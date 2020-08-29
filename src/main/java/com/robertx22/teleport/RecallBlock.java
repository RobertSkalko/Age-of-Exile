package com.robertx22.teleport;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RecallBlock extends Block {

    public RecallBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        if (player.getStackInHand(hand)
            .getItem() instanceof TeleportItem) {

            BlockPos p = Load.teleport(player).data.last_recall_pos;

            if (p == null) {
                player.sendMessage(new LiteralText("You don't have a point to travel to."), false);
            } else {
                EntityUtils.setLoc(player, p);
            }
        } else {
            Load.teleport(player).data.altar_pos = player.getBlockPos();
            player.sendMessage(new LiteralText("Recall Point Set."), false);

        }

        return ActionResult.SUCCESS;

    }

}
