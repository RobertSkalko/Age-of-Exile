package com.robertx22.age_of_exile.capability.world;

import com.robertx22.age_of_exile.areas.AreaData;
import com.robertx22.age_of_exile.areas.AreaSearch;
import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifier;
import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifiers;
import com.robertx22.age_of_exile.areas.base_areas.BaseAreas;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
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
        public List<AreaData> areas = new ArrayList<>();
    }

    World world;

    public static AreaData getArea(World world, BlockPos pos) {
        WorldAreas worldAreas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);

        pos = new ChunkPos(pos).getCenterBlockPos(); // this is important

        if (world.isClient) {
            // ask for server packet (but player should already have it in chunkloadevent??
            return worldAreas.getAreaFor(world, pos);
        } else {
            return new AreaSearch(world, pos).getOrGenerateArea();
        }

    }

    Data data = new Data();

    HashMap<ChunkPos, AreaData> chunkMap = new HashMap<>();
    HashMap<String, AreaData> idMap = new HashMap<>();

    public boolean hasArea(BlockPos pos) {
        return chunkMap.containsKey(new ChunkPos(pos));
    }

    public AreaData getAreaById(String id) {
        return idMap.get(id);
    }

    public AreaData getAreaFor(World world, BlockPos pos) {
        if (hasArea(pos)) {
            return chunkMap.get(new ChunkPos(pos));
        }
        if (world.isClient) {
            return AreaData.EMPTY;
        } else {
            throw new RuntimeException("How is the area not generated on server before getter is called?");
        }
    }

    public AreaData createNewArea(World world, Biome biome, List<ChunkPos> chunks) {
        AreaData data = new AreaData();
        data.uuid = UUID.randomUUID()
            .toString();
        data.setChunks(chunks);
        data.biome_category = biome.getCategory()
            .name();
        data.biome = world.getRegistryManager()
            .get(Registry.BIOME_KEY)
            .getId(biome)
            .toString();

        data.base_area = BaseAreas.INSTANCE.getRandomAreaFor(biome.getCategory()).id;

        AreaModifier mod = AreaModifiers.INSTANCE.getRandomFor(biome);
        if (mod != null) {
            data.area_modifier = mod.GUID();
        }

        this.data.areas.add(data);

        data.getChunks()
            .forEach(x -> chunkMap.put(x, data));
        idMap.put(data.uuid, data);

        return data;
    }

    public void updateClientValue(AreaData data) {
        this.data.areas.add(data);
        data.getChunks()
            .forEach(x -> chunkMap.put(x, data));
    }

    @Override
    public void fromTag(CompoundTag nbt) {

        this.data = LoadSave.Load(Data.class, new Data(), nbt, "areas");

        data.areas.forEach(x -> {
            idMap.put(x.uuid, x);
            x.getChunks()
                .forEach(c -> {

                    chunkMap.put(c, x);
                });
        });

    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, "areas");

        return nbt;
    }
}
