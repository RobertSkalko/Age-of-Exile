package com.robertx22.age_of_exile.areas;

import com.robertx22.age_of_exile.areas.base_areas.BaseArea;
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

    public BaseArea getBaseArea() {

        if (!BaseArea.MAP.containsKey(base_area)) {
            return BaseArea.DEFAULT;
        }

        return BaseArea.MAP.get(base_area);
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
