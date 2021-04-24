package com.robertx22.age_of_exile.dimension.teleporter.portal_block;

import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.mixin_ducks.PlayerTeleStateAccessor;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class PortalBlock extends OpaqueBlock implements BlockEntityProvider {

    public PortalBlock() {
        super(Settings.of(Material.STONE)
            .noCollision()
            .strength(5F, 2));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new PortalBlockEntity();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() + random.nextDouble();
            double f = (double) pos.getZ() + random.nextDouble();
            double g = ((double) random.nextFloat() - 0.5D) * 0.5D;
            double h = ((double) random.nextFloat() - 0.5D) * 0.5D;
            double j = ((double) random.nextFloat() - 0.5D) * 0.5D;
            int k = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west())
                .isOf(this) && !world.getBlockState(pos.east())
                .isOf(this)) {
                d = (double) pos.getX() + 0.5D + 0.25D * (double) k;
                g = (double) (random.nextFloat() * 2.0F * (float) k);
            } else {
                f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
                j = (double) (random.nextFloat() * 2.0F * (float) k);
            }

            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
        }

    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        try {

            if (entity instanceof PlayerEntity) {

                entity.teleporting = true;

                if (entity instanceof ServerPlayerEntity) {
                    if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {

                        PlayerTeleStateAccessor acc = (PlayerTeleStateAccessor) entity;
                        acc.setIsInTeleportationState(true);

                        PortalBlockEntity be = (PortalBlockEntity) world.getBlockEntity(pos);

                        if (be.dungeonPos == BlockPos.ORIGIN) {
                            return;
                        } else {

                            TeleportUtils.teleport((ServerPlayerEntity) entity, be.dungeonPos, DimensionIds.DUNGEON_DIMENSION);

                        }

                    }
                }

// todo wait 20 seconds and set teleport state to false OTHERWISE IT WONT TELEPORT AND CAUSE WEIRD BUGS

                //   acc.setIsInTeleportationState(false);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
