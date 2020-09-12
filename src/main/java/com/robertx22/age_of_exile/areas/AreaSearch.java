package com.robertx22.age_of_exile.areas;

import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class AreaSearch {

    Set<ChunkPos> matches = new HashSet<>();
    Stack<ChunkPos> stack = new Stack<>();
    Set<ChunkPos> visited = new HashSet<>();

    Biome biome;
    World world;
    BlockPos pos;

    public AreaSearch(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public AreaData getOrGenerateArea() {
        WorldAreas worldAreas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);

        if (!worldAreas.hasArea(pos)) {
            //Watch watch = new Watch();

            this.biome = world.getBiome(pos);

            branchTo(new ChunkPos(pos));

            while (!stack.isEmpty()) {
                ChunkPos cp = stack.pop();
                branchTo(cp);

                if (visited.size() > 3000) {
                    System.out.println("Biome too big, stopping");
                    break;
                }
            }

            worldAreas.createNewArea(world, biome, new ArrayList<>(matches));

            //watch.print("gen area and visiting: " + visited.size() + " takes: ");
        }
        return worldAreas.getAreaFor(world, pos);

    }

    boolean areSameBiomes(ChunkPos cp) {

        Biome other = world.getBiome(cp.getStartPos());

        if (biome == other) {
            return true;
        }

        return biome.getCategory()
            .equals(other.getCategory());
    }

    void branchTo(ChunkPos cp) {

        if (areSameBiomes(cp)) {

            if (!visited.contains(cp)) {
                matches.add(cp);
                visited.add(cp);
            }

            addIfSameBiome(new ChunkPos(cp.x - 1, cp.z));
            addIfSameBiome(new ChunkPos(cp.x + 1, cp.z));
            addIfSameBiome(new ChunkPos(cp.x, cp.z - 1));
            addIfSameBiome(new ChunkPos(cp.x, cp.z + 1));

        }

    }

    void addIfSameBiome(ChunkPos cp) {

        if (visited.contains(cp)) {
            return;
        }

        if (areSameBiomes(cp)) {
            stack.add(cp);
            matches.add(cp);
        }

        visited.add(cp);

    }

}
