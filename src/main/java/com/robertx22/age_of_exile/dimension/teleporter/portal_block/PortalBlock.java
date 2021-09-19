package com.robertx22.age_of_exile.dimension.teleporter.portal_block;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import com.robertx22.library_of_exile.utils.VanillaReg;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class PortalBlock extends OpaqueBlock implements ITileEntityProvider {

    public PortalBlock() {
        super(Properties.of(Material.STONE)
            .noCollission()
            .strength(5F, 2));
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new PortalBlockEntity();
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
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
                .is(this) && !world.getBlockState(pos.east())
                .is(this)) {
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
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        try {

            if (world.isClientSide) {
                return;
            }

            if (entity instanceof PlayerEntity) {

                RPGPlayerData maps = Load.playerRPGData((PlayerEntity) entity);

                if (entity.getDeltaMovement().y > 0) {
                    maps.maps.ticksinPortal = 0; // jumping bugs it somehow
                    return;
                }

                if (WorldUtils.isMapWorldClass(world)) {
                    if (maps.maps.ticksinPortal < 40) {
                        maps.maps.ticksinPortal++;
                    } else {
                        maps.maps.ticksinPortal = 0;
                        BlockPos p = Load.playerRPGData((PlayerEntity) entity).maps.tel_pos.above();

                        TeleportUtils.teleport((ServerPlayerEntity) entity, p, new ResourceLocation(maps.maps.tp_b_dim));

                        SoundUtils.playSound(entity, SoundEvents.PORTAL_TRAVEL, world.random.nextFloat() * 0.4F + 0.8F, 0.25F);
                        return;
                    }
                }

                PortalBlockEntity be = (PortalBlockEntity) world.getBlockEntity(pos);

                if (entity instanceof ServerPlayerEntity) {
                    if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {

                        if (maps.maps.ticksinPortal < 40) {
                            maps.maps.ticksinPortal++;
                        } else {

                            if (be.data.dungeonPos == BlockPos.ZERO) {
                                return;
                            } else {
                                maps.maps.ticksinPortal = 0;
                                maps.maps.tel_pos = be.data.tpbackpos;
                                maps.maps.tp_b_dim = VanillaReg.getId(entity.level)
                                    .toString();

                                TeleportUtils.teleport((ServerPlayerEntity) entity, be.data.dungeonPos, be.data.dungeonType.DIMENSION_ID);
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
