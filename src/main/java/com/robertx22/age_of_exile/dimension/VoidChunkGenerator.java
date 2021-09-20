package com.robertx22.age_of_exile.dimension;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VoidChunkGenerator extends ChunkGenerator {
    private final Registry<Biome> biomeRegistry;

    public static Codec<VoidChunkGenerator> VOID_CODED =
        RegistryLookupCodec.create(Registry.BIOME_REGISTRY)
            .xmap(VoidChunkGenerator::new, VoidChunkGenerator::getBiomeRegistry)
            .stable()
            .codec();

    public VoidChunkGenerator(Registry<Biome> biomeRegistry) {
        super(new SingleBiomeProvider((Biome) biomeRegistry.getOrThrow(Biomes.THE_VOID)), new DimensionStructuresSettings(false));
        this.getSettings()
            .structureConfig()
            .clear();
        this.biomeRegistry = biomeRegistry;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return VOID_CODED;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {
    }

    @Override
    public void applyCarvers(long seed, BiomeManager access, IChunk chunk, GenerationStage.Carving carver) {
    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager accessor, IChunk chunk) {

        BlockState blockState = Blocks.STONE.defaultBlockState();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        if (blockState != null) {
            for (int i = 0; i < 220; ++i) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        chunk.setBlockState(mutable.set(j, i, k), blockState, false);
                    }
                }
            }
        }
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public IBlockReader getBaseColumn(int x, int z) {
        return new Blockreader(new BlockState[0]);
    }

}
