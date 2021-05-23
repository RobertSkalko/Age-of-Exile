package com.robertx22.age_of_exile.dimension;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonPopulateData;
import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SignUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.world_of_exile.main.ModLoottables;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PopulateDungeonChunks {

    public static void tryPopulateChunksAroundPlayer(World world, PlayerEntity player) {
        if (true) {
            return;
        }
        if (WorldUtils.isDungeonWorld(world)) {

            // Watch watch = new Watch();

            int populated = 0;
            List<ChunkPos> list = getChunksAround(new ChunkPos(player.getBlockPos()));

            for (ChunkPos cp : list) {
                Chunk chunk = world.getChunk(cp.x, cp.z);

                if (!Load.chunkPopulated(chunk).populated) {
                    DungeonData data = Load.dungeonData(world).data.get(chunk.getPos()
                        .getStartPos()).data;

                    if (!data.isEmpty()) {
                        PopulateDungeonChunks.populateChunk(world, chunk, data, new DungeonPopulateData());
                        Load.chunkPopulated(chunk).populated = true;
                        populated++;
                        if (populated > 5) {
                            //watch.print("Populating chunk around player ");
                            break;
                        }

                    }
                }
            }

        }

    }

    public static List<ChunkPos> getChunksAround(ChunkPos cp) {
        List<ChunkPos> list = new ArrayList<>();
        list.add(cp);

        list.add(new ChunkPos(cp.x + 1, cp.z));
        list.add(new ChunkPos(cp.x, cp.z + 1));

        list.add(new ChunkPos(cp.x - 1, cp.z));
        list.add(new ChunkPos(cp.x, cp.z - 1));

        list.add(new ChunkPos(cp.x + 1, cp.z + 1));
        list.add(new ChunkPos(cp.x - 1, cp.z - 1));

        list.add(new ChunkPos(cp.x - 1, cp.z + 1));
        list.add(new ChunkPos(cp.x + 1, cp.z - 1));

        return list;

    }

    public static void populate(Set<DungeonPopulateData.CP> toPopulate, Set<DungeonPopulateData.CP> populated, World world, Chunk chunk, DungeonData dungeon, DungeonPopulateData data) {

        boolean has = populateChunk(world, chunk, dungeon, data);

        if (has) {
            populated.add(new DungeonPopulateData.CP(chunk.getPos()));

            getChunksAround(chunk.getPos()).forEach(x -> toPopulate.add(new DungeonPopulateData.CP(x)));
        }
        toPopulate.removeIf(x -> x.getChunkPos()
            .equals(chunk.getPos()));
    }

    public static boolean populateChunk(World world, Chunk chunk, DungeonData dungeon, DungeonPopulateData data) {

        Set<BlockPos> list = chunk.getBlockEntityPositions();

        boolean has = !list.isEmpty();

        for (BlockPos blockPos : list) {
            BlockEntity be = world.getBlockEntity(blockPos);
            if (be instanceof BeaconBlockEntity) {
                try {
                    populate(world, blockPos, dungeon, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (be instanceof SignBlockEntity) {
                SignBlockEntity sign = (SignBlockEntity) be;
                if (SignUtils.has("[chest]", sign)) {
                    setChest(world, blockPos);
                }
            }
        }

        return has;

    }

    static void populate(World world, BlockPos pos, DungeonData dungeonData, DungeonPopulateData data) {

        data.pieces++;

        List<BlockPos> list = new ArrayList<>();
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                list.add(pos.add(x, 0, z));
            }
        }

        int mobs = RandomUtils.RandomRange(2, 5);
        int chests = RandomUtils.roll(20) ? 1 : 0;
        int spawners = RandomUtils.roll(20) ? 1 : 0;

        int tries = 0;
        for (int i = 0; i < mobs; i++) {
            BlockPos p = RandomUtils.randomFromList(list);

            tries++;
            if (tries > 50) {
                break;
            }

            EntityType type = dungeonData.getMobList()
                .getRandomMob();

            if (!SpawnUtil.canPlaceMob(world, type, p)) {
                i--;
                continue;
            }
            data.mobs++;

            dungeonData.getMobList()
                .spawMob((ServerWorld) world, type, p, dungeonData.t);

            list.remove(p);
        }

        tries = 0;

        for (int i = 0; i < chests; i++) {
            BlockPos p = RandomUtils.randomFromList(list);
            tries++;
            if (tries > 50) {
                break;
            }

            if (!SpawnUtil.canPlaceBlock(world, p)) {
                i--;
                continue;
            }
            data.chests++;

            setChest(world, p);

            list.remove(p);
        }

        tries = 0;

        for (int i = 0; i < spawners; i++) {
            BlockPos p = RandomUtils.randomFromList(list);
            tries++;
            if (tries > 50) {
                break;
            }
            if (!SpawnUtil.canPlaceBlock(world, p)) {
                i--;
                continue;
            }
            data.mobs += ModSpawnerBlockEntity.DEFAULT_SPAWNS;

            world.setBlockState(p, ModRegistry.BLOCKS.SPAWNER.getDefaultState(), 2);

            list.remove(p);
        }

        world.breakBlock(pos, false);

    }

    public static void setChest(World world, BlockPos p) {
        world.setBlockState(p, Blocks.CHEST.getDefaultState(), 2);
        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(p);
        chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, world.random.nextLong());

    }
}
