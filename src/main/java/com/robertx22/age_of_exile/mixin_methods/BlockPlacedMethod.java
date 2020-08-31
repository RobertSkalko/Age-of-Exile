package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.mobs.bosses.bases.IBossMob;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

public class BlockPlacedMethod {
    public static void hook(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        try {
            if (!world.isClient) {

                boolean bl = state.isOf(Blocks.BONE_BLOCK);

                if (bl && pos.getY() >= 0 && world.getDifficulty() != Difficulty.PEACEFUL) {

                    for (IBossMob.BossAndPattern info : IBossMob.ALL_PATTERNS) {
                        BlockPattern pattern = info.pattern;

                        BlockPattern.Result result = pattern.searchAround(world, pos);
                        if (result != null) {
                            for (int i = 0; i < pattern.getWidth(); ++i) {
                                for (int j = 0; j < pattern.getHeight(); ++j) {
                                    CachedBlockPosition cachedBlockPosition = result.translate(i, j, 0);
                                    world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                                    world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
                                }
                            }

                            MobEntity bossEntity = info.type.create(world);
                            BlockPos blockPos = result.translate(1, 2, 0)
                                .getBlockPos();
                            bossEntity.refreshPositionAndAngles((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.55D, (double) blockPos.getZ() + 0.5D, result.getForwards()
                                .getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
                            bossEntity.bodyYaw = result.getForwards()
                                .getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
                            Iterator var13 = world.getNonSpectatingEntities(ServerPlayerEntity.class, bossEntity.getBoundingBox()
                                .expand(50.0D))
                                .iterator();

                            while (var13.hasNext()) {
                                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var13.next();
                                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, bossEntity);
                            }

                            world.spawnEntity(bossEntity);

                            for (int k = 0; k < pattern.getWidth(); ++k) {
                                for (int l = 0; l < pattern.getHeight(); ++l) {
                                    world.updateNeighbors(result.translate(k, l, 0)
                                        .getBlockPos(), Blocks.AIR);
                                }
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
