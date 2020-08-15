package com.robertx22.age_of_exile.capability.world;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class WorldAreas implements Component {

    public WorldAreas(World world) {
        this.world = world;
    }

    @Storable
    static class Data {
        @Store
        public List<ChunkAreaData> areas = new ArrayList<>();
    }

    World world;

    public static ChunkAreaData getArea(World world, BlockPos pos) {
        WorldAreas worldAreas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);

        pos = new ChunkPos(pos).getCenterBlockPos(); // this is important

        if (!worldAreas.hasArea(pos)) {
            Watch watch = new Watch();

            Set<ChunkPos> matches = new HashSet<>();
            Stack<ChunkPos> chunks = new Stack<>();
            Set<ChunkPos> visited = new HashSet<>();

            Biome biome = world.getBiome(pos);

            branchTo(world, biome, matches, chunks, visited, new ChunkPos(pos));

            while (!chunks.isEmpty()) {
                branchTo(world, biome, matches, chunks, visited, chunks.pop());
            }

            worldAreas.createNewArea(biome, new ArrayList<>(matches));

            watch.print("gen area takes: ");

        }

        return worldAreas.getArea(pos);
    }

    static void branchTo(World world, Biome biome, Set<ChunkPos> matches, Stack<ChunkPos> chunks, Set<ChunkPos> visited, ChunkPos cp) {

        if (isSameBiome(world, biome, cp) && !visited.contains(cp)) {
            chunks.push(cp);
            matches.add(cp);
            visited.add(cp);
        }

        ChunkPos left = new ChunkPos(cp.x - 1, cp.z);
        ChunkPos right = new ChunkPos(cp.x + 1, cp.z);
        ChunkPos up = new ChunkPos(cp.x, cp.z - 1);
        ChunkPos down = new ChunkPos(cp.x, cp.z + 1);

        isSameBiomeAdd(world, biome, left, matches, chunks, visited);
        isSameBiomeAdd(world, biome, right, matches, chunks, visited);
        isSameBiomeAdd(world, biome, up, matches, chunks, visited);
        isSameBiomeAdd(world, biome, down, matches, chunks, visited);
    }

    static void isSameBiomeAdd(World world, Biome biome, ChunkPos cp, Set<ChunkPos> matches, Stack<ChunkPos> chunks, Set<ChunkPos> visited) {
        boolean same = world.getBiome(cp.getCenterBlockPos()) == biome;

        if (visited.contains(cp)) {
            return;
        }

        if (same) {
            chunks.add(cp);
            matches.add(cp);
        }
        visited.add(cp);

    }

    static boolean isSameBiome(World world, Biome biome, ChunkPos cp) {
        return world.getBiome(cp.getCenterBlockPos()) == biome;
    }

    Data data = new Data();

    HashMap<ChunkPos, ChunkAreaData> map = new HashMap<>();

    public boolean hasArea(BlockPos pos) {

        return map.containsKey(new ChunkPos(pos));
    }

    public ChunkAreaData getArea(BlockPos pos) {
        return map.get(new ChunkPos(pos));
    }

    public ChunkAreaData createNewArea(Biome biome, List<ChunkPos> chunks) {
        ChunkAreaData data = new ChunkAreaData();
        data.uuid = UUID.randomUUID()
            .toString();
        data.setChunks(chunks);

        return data;
    }

    @Override
    public void fromTag(CompoundTag nbt) {

        this.data = LoadSave.Load(Data.class, new Data(), nbt, "areas");

        data.areas.forEach(x -> {
            x.getChunks()
                .forEach(c -> map.put(c, x));
        });

    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, "areas");

        return nbt;
    }
}
