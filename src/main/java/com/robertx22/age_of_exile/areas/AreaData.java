package com.robertx22.age_of_exile.areas;

import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifier;
import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifiers;
import com.robertx22.age_of_exile.areas.base_areas.BaseArea;
import com.robertx22.age_of_exile.areas.base_areas.BaseAreas;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class AreaData {

    public static AreaData EMPTY = new AreaData();

    @Store
    public String uuid = "";
    @Store
    public String biome_category = "";
    @Store
    public String base_area = "";
    @Store
    public String area_modifier = "";

    public BaseArea getBaseArea() {

        if (!BaseAreas.INSTANCE.MAP.containsKey(base_area)) {
            return BaseAreas.INSTANCE.DEFAULT;
        }

        return BaseAreas.INSTANCE.MAP.get(base_area);
    }

    public AreaModifier getAreaModifier() {

        if (!AreaModifiers.INSTANCE.MAP.containsKey(area_modifier)) {
            return AreaModifiers.INSTANCE.PLAIN;
        }

        return AreaModifiers.INSTANCE.MAP.get(area_modifier);
    }

    @Store
    private List<BlockPos> chunks = new ArrayList<>();

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
