package com.robertx22.age_of_exile.dimension;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonPopulateData;
import com.robertx22.age_of_exile.dimension.dungeon_data.SingleDungeonData;
import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.world_of_exile.main.ModLoottables;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PopulateDungeonChunks {

    public static void populateAll(World world, ChunkPos cpos, SingleDungeonData data) {

        Watch watch = new Watch();

        List<ChunkPos> toPopulate = new ArrayList<>();
        List<ChunkPos> populated = new ArrayList<>();

        toPopulate.addAll(getChunksAround(cpos));

        int tries = 0;

        while (!toPopulate.isEmpty()) {

            tries++;
            if (tries > 100) {
                break;
            }

            ChunkPos cp = toPopulate.get(0);
            if (!populated.contains(cp)) {
                populate(toPopulate, populated, world, world.getChunk(cp.x, cp.z), data.data, data.pop);
            } else {
                toPopulate.removeIf(x -> x.equals(cp));
            }
        }

        watch.print("populating the whole dungeon ");

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

    public static void populate(List<ChunkPos> toPopulate, List<ChunkPos> populated, World world, Chunk chunk, DungeonData dungeon, DungeonPopulateData data) {

        Set<BlockPos> list = chunk.getBlockEntityPositions();

        boolean has = !list.isEmpty();

        for (BlockPos blockPos : list) {
            BlockEntity be = world.getBlockEntity(blockPos);
            if (be instanceof BeaconBlockEntity) {
                populate(world, blockPos, dungeon, data);
            }
        }

        if (has) {
            populated.add(chunk.getPos());
            toPopulate.addAll(getChunksAround(chunk.getPos()));
        }

        toPopulate.removeIf(x -> x.equals(chunk.getPos()));
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

        boolean isboss = false;
        if (RandomUtils.roll(100)) {
            isboss = true;
            mobs = 1;
            spawners = 0;
        }

        int tries = 0;
        for (int i = 0; i < mobs; i++) {
            BlockPos p = RandomUtils.randomFromList(list);

            tries++;
            if (tries > 50) {
                break;
            }
            if (!world.isAir(p) && !world.getBlockState(p.down())
                .isSolidBlock(world, p.down())) {
                i--;
                continue;
            }
            data.mobs++;

            if (isboss) {
                dungeonData.getMobList()
                    .spawnBoss((ServerWorld) world, p, dungeonData.tier);
            } else {
                dungeonData.getMobList()
                    .spawnRandomMob((ServerWorld) world, p, dungeonData.tier);
            }

            list.remove(p);
        }

        tries = 0;

        for (int i = 0; i < chests; i++) {
            BlockPos p = RandomUtils.randomFromList(list);
            tries++;
            if (tries > 50) {
                break;
            }
            if (!world.isAir(p.up()) && !world.isAir(p) && world.getBlockState(p.down())
                .isSolidBlock(world, p.down())) {
                i--;
                continue;
            }
            data.chests++;

            world.setBlockState(p, Blocks.CHEST.getDefaultState(), 2);

            ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(p);

            chest.setLootTable(ModLoottables.DUNGEON_DEFAULT, world.random.nextLong());

            list.remove(p);
        }

        tries = 0;

        for (int i = 0; i < spawners; i++) {
            BlockPos p = RandomUtils.randomFromList(list);
            tries++;
            if (tries > 50) {
                break;
            }
            if (!world.isAir(p.up()) && !world.isAir(p) && world.getBlockState(p.down())
                .isSolidBlock(world, p.down())) {
                i--;
                continue;
            }

            data.mobs += ModSpawnerBlockEntity.DEFAULT_SPAWNS;

            world.setBlockState(p, ModRegistry.BLOCKS.SPAWNER.getDefaultState(), 2);

            list.remove(p);
        }

        world.breakBlock(pos, false);

    }

}
