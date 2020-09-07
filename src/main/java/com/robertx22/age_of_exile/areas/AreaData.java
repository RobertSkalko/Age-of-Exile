package com.robertx22.age_of_exile.areas;

import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifier;
import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifiers;
import com.robertx22.age_of_exile.areas.base_areas.BaseArea;
import com.robertx22.age_of_exile.areas.base_areas.BaseAreas;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;

import java.util.*;
import java.util.stream.Collectors;

@Storable
public class AreaData {

    public static AreaData EMPTY = new AreaData();

    @Store
    private List<BlockPos> chunks = new ArrayList<>();
    @Store
    public String uuid = "";
    @Store
    public String biome_category = "";
    @Store
    public String biome = "";
    @Store
    public String base_area = "";
    @Store
    public String area_modifier = "";

    public String getName() {
        return getAreaModifier().getFinalLocNameFor(getBaseArea());
    }

    public MinMax getLevelRange(World world, PlayerEntity player) {

        if (!Cached.AREA_LEVEL_RANGE.containsKey(this.uuid)) {

            Set<Integer> list = new HashSet<>();

            getChunks().forEach(x -> {
                list.add(LevelUtils.determineLevel(world, x.getCenterBlockPos(), player));
            });

            if (list.isEmpty()) {
                return new MinMax(0, 0);
            }

            MinMax minmax = new MinMax(MathHelper.clamp(
                list.stream()
                    .min(Comparator.comparingInt(x -> x))
                    .get(), 1, ModConfig.get().Server.MAX_LEVEL),
                MathHelper.clamp(list.stream()
                    .max(Comparator.comparingInt(x -> x))
                    .get(), 1, ModConfig.get().Server.MAX_LEVEL)
            );

            Cached.AREA_LEVEL_RANGE.put(uuid, minmax);

        }

        return Cached.AREA_LEVEL_RANGE.get(uuid);

    }

    public Biome getBiome(World world) {
        try {
            return world.getRegistryManager()
                .get(Registry.BIOME_KEY)
                .get(new Identifier(biome));
        } catch (Exception e) {
            e.printStackTrace();
            return BuiltinBiomes.PLAINS;
        }
    }

    public BaseArea getBaseArea() {

        if (!BaseAreas.INSTANCE.MAP.containsKey(base_area)) {
            return BaseAreas.INSTANCE.DEFAULT;
        }

        return BaseAreas.INSTANCE.MAP.get(base_area);
    }

    public AreaModifier getAreaModifier() {

        AreaModifier area = AreaModifiers.INSTANCE.MAP.get(area_modifier);

        if (area != null) {
            return area;
        }

        return AreaModifiers.INSTANCE.PLAIN;
    }

    public void setChunks(List<ChunkPos> chunks) {
        this.chunks = chunks.stream()
            .map(x -> x.getCenterBlockPos())
            .collect(Collectors.toList());
    }

    public List<ChunkPos> getChunks() {
        return chunks.stream()
            .map(x -> new ChunkPos(x))
            .collect(Collectors.toList());
    }

}
