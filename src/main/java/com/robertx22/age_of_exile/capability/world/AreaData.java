package com.robertx22.age_of_exile.capability.world;

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
    public String biome = "";
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
