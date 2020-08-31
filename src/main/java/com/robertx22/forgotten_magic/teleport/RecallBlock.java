package com.robertx22.forgotten_magic.teleport;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.block.BlockStatePredicate;
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

    private static BlockPattern pattern;

    // copied from witherskullblock
    public static BlockPattern getMultiblockPattern() {
        if (pattern == null) {// TODO testing multibloc
            pattern = BlockPatternBuilder.start()
                .aisle("G   G", "     ", "     ", "     ", "G   G")
                .aisle("C   C", "     ", "     ", "     ", "C   C")
                .aisle("C   C", "     ", "     ", "     ", "C   C")
                .aisle("PPPPP", "PPPPP", "PPRPP", "PPPPP", "PPPPP")
                .where('G', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.GILDED_BLACKSTONE)))
                .where('P', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.POLISHED_BLACKSTONE_BRICKS)))
                .where('R', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(ModRegistry.BLOCKS.RECALL_BLOCK)))
                .where(' ', x -> true)
                .where('C', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.CHISELED_POLISHED_BLACKSTONE)))
                .build();
        }
        return pattern;
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
