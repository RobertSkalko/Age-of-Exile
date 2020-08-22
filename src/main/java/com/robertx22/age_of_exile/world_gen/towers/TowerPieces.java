package com.robertx22.age_of_exile.world_gen.towers;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModWorldGen;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.world_gen.towers.processors.BiomeProcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.*;

public class TowerPieces {

    private static Identifier MID1 = new Identifier(Ref.MODID, "tower/middle/air_floor");
    private static Identifier START1 = new Identifier(Ref.MODID, "tower/start/lava");
    private static Identifier TOP1 = new Identifier(Ref.MODID, "tower/top/simple_fort");
    private static Identifier FOUNDATION = new Identifier(Ref.MODID, "tower/foundation");

    static List<Identifier> ALL_MIDS = Arrays.asList(MID1);
    static List<Identifier> ALL_STARTS = Arrays.asList(START1);
    static List<Identifier> ALL_TOPS = Arrays.asList(TOP1);

    static int FOUNDATION_SIZE = 8;

    static Identifier randomOf(List<Identifier> list, Random ran) {
        return RandomUtils.randomFromList(list, ran);
    }

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random rand) {
        int amount = rand.nextInt(4) + 2;

        pos = pos.add(0, -FOUNDATION_SIZE, 0);

        for (int i = 0; i < FOUNDATION_SIZE; i++) {
            pieces.add(new Piece(manager, FOUNDATION, pos = pos.add(0, 1, 0), rotation));
        }

        Identifier startid = randomOf(ALL_STARTS, rand);
        Structure startstruc = manager.getStructure(startid);
        int startheight = startstruc.getSize()
            .getY();

        BlockRotation curRot = rotation;

        pieces.add(new Piece(manager, startid, pos, rotation));
        pos = pos.add(0, startheight, 0);

        for (int j = 0; j < amount - 1; ++j) {

            Identifier id = randomOf(ALL_MIDS, rand);
            Structure struc = manager.getStructure(id);

            BlockPos p = pos;

            pieces.add(new Piece(manager, id, p, curRot));

            int height = struc.getSize()
                .getY();
            pos = pos.add(0, height, 0);
        }

        Identifier topid = randomOf(ALL_TOPS, rand);
        pieces.add(new Piece(manager, topid, pos, curRot));

    }

    public static class Piece extends SimpleStructurePiece {
        private Identifier template;
        private BlockRotation rotation;

        public Piece(StructureManager manager, Identifier identifier, BlockPos pos, BlockRotation rotation) {
            super(ModWorldGen.INSTANCE.TOWER_PIECE, 0);
            this.template = identifier;
            this.pos = pos;
            this.rotation = rotation;
            this.initializeStructureData(manager);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(ModWorldGen.INSTANCE.TOWER_PIECE, tag);
            this.template = new Identifier(tag.getString("Template"));
            this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
            this.initializeStructureData(manager);
        }

        private void initializeStructureData(StructureManager manager) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData data = (new StructurePlacementData()).setRotation(this.rotation)
                .setMirror(BlockMirror.NONE)
                .setPosition(new BlockPos(0, 0, 0))
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS)
                .addProcessor(new BiomeProcessor(""));

            this.setStructureData(structure, this.pos, data);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());

        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {

        }

        @Override
        public boolean generate(StructureWorldAccess waccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {

            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(this.rotation)
                .setMirror(BlockMirror.NONE)
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            BlockPos blockPos3 = this.pos.add(Structure.transform(structurePlacementData, new BlockPos(0, 0, 0)));

            List<Integer> surfaces = new ArrayList<>();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    surfaces.add(waccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, blockPos3.getX() + x, blockPos3.getZ() + z));
                }
            }
            int highest = surfaces.stream()
                .max(Comparator.comparingInt(x -> x))
                .get();

            if (surfaces.stream()
                .anyMatch(x -> Math.abs(x - highest) > FOUNDATION_SIZE)) {
                return false;

            } else {
                int surfaceY = highest;
                BlockPos blockPos4 = this.pos;
                this.pos = this.pos.add(0, surfaceY - 90 - 1, 0);
                boolean bl = super.generate(waccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);

                this.pos = blockPos4;
                return bl;
            }
        }
    }

}
