package com.robertx22.magic_mod.furnace;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseInventoryBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class EssentiaFurnaceBlock extends BaseInventoryBlock {

    public EssentiaFurnaceBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    // copied from enderchestblock
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 6; ++i) {
            int j = random.nextInt(2) * 2 - 1;
            int k = random.nextInt(2) * 2 - 1;
            double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
            double e = (double) ((float) pos.getY() + random.nextFloat());
            double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
            double g = (double) (random.nextFloat() * (float) j);
            double h = ((double) random.nextFloat() - 0.5D) * 0.125D;
            double l = (double) (random.nextFloat() * (float) k);
            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, l);
        }

    }

    @Override
    public Identifier getContainerId() {
        return ModRegistry.CONTAINERS.ESSENTIA_FURNACE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EssentiaFurnaceBlockEntity();
    }
}
