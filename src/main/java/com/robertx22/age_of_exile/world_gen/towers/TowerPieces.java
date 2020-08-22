package com.robertx22.age_of_exile.world_gen.towers;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModWorldGen;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TowerPieces {

    private static Identifier MID1 = new Identifier(Ref.MODID, "tower/middle/air_floor");
    private static Identifier START1 = new Identifier(Ref.MODID, "tower/start/lava");

    static List<Identifier> ALL_MIDS = Arrays.asList(MID1);
    static List<Identifier> ALL_STARTS = Arrays.asList(START1);

    static Identifier randomOf(List<Identifier> list, Random ran) {
        return RandomUtils.randomFromList(list, ran);
    }

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random rand) {
        int amount = rand.nextInt(4) + 3;

        Identifier startid = randomOf(ALL_MIDS, rand);
        Structure startstruc = manager.getStructure(startid);
        int startheight = startstruc.getSize()
            .getY();

        pieces.add(new Piece(manager, randomOf(ALL_STARTS, rand), pos, rotation, amount * 3));

        int heightOffset = startheight;

        for (int j = 0; j < amount - 1; ++j) {
            Identifier id = randomOf(ALL_MIDS, rand);
            Structure struc = manager.getStructure(id);

            pieces.add(new Piece(manager, id, pos, rotation, j * heightOffset));

            int height = struc.getSize()
                .getY();

            heightOffset += height;
        }

        //  pieces.add(new Piece(manager, TOP_TEMPLATE, pos, rotation, 0));
    }

    public static class Piece extends SimpleStructurePiece {
        private Identifier template;
        private BlockRotation rotation;
        int yOffset;

        public Piece(StructureManager manager, Identifier identifier, BlockPos pos, BlockRotation rotation, int yOffset) {
            super(ModWorldGen.INSTANCE.TOWER_PIECE, 0);
            this.template = identifier;
            this.pos = pos.add(0, yOffset, 0);
            this.rotation = rotation;
            this.initializeStructureData(manager, yOffset);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(ModWorldGen.INSTANCE.TOWER_PIECE, tag);
            this.template = new Identifier(tag.getString("Template"));
            this.rotation = BlockRotation.valueOf(tag.getString("Rot"));
            this.yOffset = tag.getInt("y_off");
            this.initializeStructureData(manager, yOffset);
        }

        private void initializeStructureData(StructureManager manager, int y) {
            Structure structure = manager.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(this.rotation)
                .setMirror(BlockMirror.NONE)
                .setPosition(new BlockPos(0, y, 0))
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, structurePlacementData);
        }

        @Override
        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());
            tag.putInt("y_off", yOffset);
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {

        }

        BlockPos getPos() {
            return new BlockPos(0, yOffset, 0);
        }

        @Override
        public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {

            BlockPos blockPos2 = getPos();
            int surfaceY = structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, blockPos2.getX(), blockPos2.getZ());
            BlockPos blockPos4 = this.pos;
            this.pos = this.pos.add(0, surfaceY - 90 - 1, 0);
            boolean bl = super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);

            this.pos = blockPos4;
            return bl;
        }
    }
}
