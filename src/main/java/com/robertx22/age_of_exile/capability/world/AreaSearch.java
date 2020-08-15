package com.robertx22.age_of_exile.capability.world;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class AreaSearch {
    public static ChunkAreaData getOrGenerateArea(World world, BlockPos pos) {
        WorldAreas worldAreas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);

        if (!worldAreas.hasArea(pos)) {
            Watch watch = new Watch();

            Set<ChunkPos> matches = new HashSet<>();
            Stack<ChunkPos> stack = new Stack<>();
            Set<ChunkPos> visited = new HashSet<>();

            Biome biome = world.getBiome(pos);

            branchTo(world, biome, matches, stack, visited, new ChunkPos(pos));

            while (!stack.isEmpty()) {
                ChunkPos cp = stack.pop();
                branchTo(world, biome, matches, stack, visited, cp);

                if (visited.size() > 2000) {
                    System.out.println("Biome too big, stopping");
                    break;
                }
            }

            worldAreas.createNewArea(biome, new ArrayList<>(matches));

            watch.print("gen area takes: ");
        }
        return worldAreas.getAreaFor(world, pos);

    }

    static boolean areSameBiomes(World world, ChunkPos cp, Biome biome) {

        Biome other = world.getBiome(cp.getCenterBlockPos());

        if (biome == other) {
            return true;
        }

        return biome.getCategory()
            .equals(other.getCategory());
    }

    static void branchTo(World world, Biome biome, Set<ChunkPos> matches, Stack<ChunkPos> stack, Set<ChunkPos> visited, ChunkPos cp) {

        if (areSameBiomes(world, cp, biome)) {

            shouldAdd(world, biome, cp, matches, stack, visited);

            ChunkPos left = new ChunkPos(cp.x - 1, cp.z);
            ChunkPos right = new ChunkPos(cp.x + 1, cp.z);
            ChunkPos up = new ChunkPos(cp.x, cp.z - 1);
            ChunkPos down = new ChunkPos(cp.x, cp.z + 1);
            ChunkPos d1 = new ChunkPos(cp.x + 1, cp.z + 1);
            ChunkPos d2 = new ChunkPos(cp.x - 1, cp.z - 1);
            ChunkPos d3 = new ChunkPos(cp.x + 1, cp.z - 1);
            ChunkPos d4 = new ChunkPos(cp.x - 1, cp.z + 1);

            shouldAdd(world, biome, left, matches, stack, visited);
            shouldAdd(world, biome, right, matches, stack, visited);
            shouldAdd(world, biome, up, matches, stack, visited);
            shouldAdd(world, biome, down, matches, stack, visited);
            shouldAdd(world, biome, d1, matches, stack, visited);
            shouldAdd(world, biome, d2, matches, stack, visited);
            shouldAdd(world, biome, d3, matches, stack, visited);
            shouldAdd(world, biome, d4, matches, stack, visited);

        }
    }

    static boolean shouldAdd(World world, Biome biome, ChunkPos cp, Set<ChunkPos> matches, Stack<ChunkPos> stack, Set<ChunkPos> visited) {
        boolean sameBiome = areSameBiomes(world, cp, biome);

        if (visited.contains(cp)) {
            return false;
        }

        if (sameBiome) {
            stack.add(cp);
            matches.add(cp);
        }

        visited.add(cp);

        return sameBiome;
    }

}
