package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.AutoHashClass;
import com.robertx22.age_of_exile.dimension.PopulateDungeonChunks;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

@Storable
public class DungeonPopulateData {

    @Store
    public Set<CP> toPopulate = new HashSet<>();
    @Store
    public Set<CP> populated = new HashSet<>();

    @Store
    public int ticks = 0;

    @Store
    public int mobs = 0;
    @Store
    public int chests = 0;

    @Store
    public int pieces = 0;

    @Store
    public boolean donePop = false;

    @Storable
    public static class CP extends AutoHashClass {
        @Store
        public int x = 0;
        @Store
        public int z = 0;

        public CP() {
        }

        public CP(ChunkPos cp) {
            this.x = cp.x;
            this.z = cp.z;
        }

        public ChunkPos getChunkPos() {
            return new ChunkPos(x, z);
        }
    }

    public void startPopulating(ChunkPos cp) {
        PopulateDungeonChunks.getChunksAround(cp)
            .forEach(x -> toPopulate.add(new CP(x)));

    }

    public void onTick(World world, SingleDungeonData data) {

        if (!toPopulate.isEmpty()) {
            ticks++;

            int neededTicks = 3;

            if (populated.size() < 10) {
                neededTicks = 1; // populate fast the first parts only
            }

            if (ticks > neededTicks) {
                ticks = 0;
                populateOne(world, data);

                if (toPopulate.isEmpty()) {
                    this.populated.clear();
                    this.toPopulate.clear();

                    this.donePop = true;

                    int kills = (int) (mobs * 0.5F);
                    data.quest.target = kills;

                    System.out.print("dungeon done populating");
                }
            }
        }
    }

    private void populateOne(World world, SingleDungeonData data) {
        ChunkPos cp = toPopulate.iterator()
            .next()
            .getChunkPos();

        if (!populated.contains(new CP(cp))) {
            PopulateDungeonChunks.populate(toPopulate, populated, world, world.getChunk(cp.x, cp.z), data.data, data.pop);
        } else {
            toPopulate.removeIf(x -> x.equals(new CP(cp)));
        }
    }
}
