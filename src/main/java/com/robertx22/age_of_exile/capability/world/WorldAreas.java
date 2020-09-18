package com.robertx22.age_of_exile.capability.world;

import com.robertx22.age_of_exile.areas.AreaData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class WorldAreas implements Component {

    public WorldAreas(World world) {
        this.world = world;
    }

    @Storable
    static class Data {
        @Store
        public HashMap<String, AreaData> biome_map = new HashMap<>();
    }

    World world;

    public static AreaData getArea(World world, BlockPos pos) {
        WorldAreas worldAreas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);
        return worldAreas.getAreaFor(world, pos);
    }

    public static void clear(World world) {
        WorldAreas worldAreas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);
        worldAreas.data.biome_map.clear();
    }

    Data data = new Data();

    public AreaData getAreaFor(World world, BlockPos pos) {

        if (world.isClient) {
            return AreaData.EMPTY;
        } else {

            Biome biome = world.getBiome(pos);
            String key = getKey(biome);

            if (!data.biome_map.containsKey(key)) {
                data.biome_map.put(key, AreaData.random(biome));
            }
            return data.biome_map.get(key);
        }
    }

    public String getKey(Biome biome) { // if it works..
        return biome.getCategory()
            .name() + "_" + biome.getDepth() + "_" + biome.getTemperature() + "_" + biome.getScale();
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.data = LoadSave.Load(Data.class, new Data(), nbt, "areas");
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, "areas");
        return nbt;
    }
}
