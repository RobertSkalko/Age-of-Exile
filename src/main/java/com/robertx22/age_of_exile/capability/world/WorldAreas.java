package com.robertx22.age_of_exile.capability.world;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

        if (world.isClient) {
            // ask for server packet (but player should already have it in chunkloadevent??
            return worldAreas.getAreaFor(world, pos);
        } else {
            return AreaSearch.getOrGenerateArea(world, pos);
        }

    }

    Data data = new Data();

    HashMap<ChunkPos, ChunkAreaData> map = new HashMap<>();

    public boolean hasArea(BlockPos pos) {

        return map.containsKey(new ChunkPos(pos));
    }

    public ChunkAreaData getAreaFor(World world, BlockPos pos) {
        if (hasArea(pos)) {
            return map.get(new ChunkPos(pos));
        }
        if (world.isClient) {
            return ChunkAreaData.EMPTY;
        } else {
            throw new RuntimeException("How is the area not generated on server before getter is called?");
        }
    }

    public ChunkAreaData createNewArea(Biome biome, List<ChunkPos> chunks) {
        ChunkAreaData data = new ChunkAreaData();
        data.uuid = UUID.randomUUID()
            .toString();
        data.setChunks(chunks);
        data.biome = BuiltinRegistries.BIOME.getId(biome)
            .toString();

        this.data.areas.add(data);

        data.getChunks()
            .forEach(x -> map.put(x, data));

        return data;
    }

    public void updateClientValue(ChunkAreaData data) {
        this.data.areas.add(data);
        data.getChunks()
            .forEach(x -> map.put(x, data));
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
