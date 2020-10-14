package com.robertx22.age_of_exile.mobs.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

import java.util.Random;

public class GriefBlocksGoal extends MoveToTargetPosGoal {
    private final Block targetBlock;
    private final MobEntity stepAndDestroyMob;
    private int counter;

    public GriefBlocksGoal(Block targetBlock, PathAwareEntity mob, double speed, int maxYDifference) {
        super(mob, speed, 24, maxYDifference);
        this.targetBlock = targetBlock;
        this.stepAndDestroyMob = mob;
    }

    @Override
    public boolean canStart() {
        if (!this.stepAndDestroyMob.world.getGameRules()
            .getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return false;
        } else if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else if (this.hasAvailableTarget()) {
            this.cooldown = 20;
            return true;
        } else {
            this.cooldown = this.getInterval(this.mob);
            return false;
        }
    }

    private boolean hasAvailableTarget() {
        return this.targetPos != null && this.isTargetPos(this.mob.world, this.targetPos) ? true : this.findTargetPos();
    }

    @Override
    public void stop() {
        super.stop();
        this.stepAndDestroyMob.fallDistance = 1.0F;
    }

    @Override
    public void start() {
        super.start();
        this.counter = 0;
    }

    @Override
    public double getDesiredSquaredDistanceToTarget() {
        return 8;
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.stepAndDestroyMob.world;
        BlockPos blockPos = this.stepAndDestroyMob.getBlockPos();
        BlockPos blockPos2 = null;
        if (targetPos != null && isTargetPos(this.mob.world, this.targetPos)) {
            blockPos2 = targetPos;
        }
        Random random = this.stepAndDestroyMob.getRandom();
        if (this.hasReached() && blockPos2 != null) {
            Vec3d vec3d2;
            double e;
            if (this.counter > 0) {
                vec3d2 = this.stepAndDestroyMob.getVelocity();
                this.stepAndDestroyMob.setVelocity(vec3d2.x, 0.3D, vec3d2.z);
                if (!world.isClient) {
                    e = 0.08D;
                    ((ServerWorld) world).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.EGG)), (double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.7D, (double) blockPos2.getZ() + 0.5D, 3, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
                }
            }

            if (this.counter % 2 == 0) {
                vec3d2 = this.stepAndDestroyMob.getVelocity();
                this.stepAndDestroyMob.setVelocity(vec3d2.x, -0.3D, vec3d2.z);

            }

            if (this.counter > 60) {
                world.breakBlock(blockPos2, true, this.stepAndDestroyMob);
                if (!world.isClient) {
                    for (int i = 0; i < 20; ++i) {
                        e = random.nextGaussian() * 0.02D;
                        double f = random.nextGaussian() * 0.02D;
                        double g = random.nextGaussian() * 0.02D;
                        ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, (double) blockPos2.getX() + 0.5D, (double) blockPos2.getY(), (double) blockPos2.getZ() + 0.5D, 1, e, f, g, 0.15000000596046448D);
                    }

                }
            }

            ++this.counter;
        }

    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false);
        if (chunk == null) {
            return false;
        } else {

            BlockState state = chunk.getBlockState(pos);

            if (state.getLuminance() > 0) {
                return true;
            }

            return chunk.getBlockState(pos)
                .isOf(this.targetBlock);
        }
    }
}

