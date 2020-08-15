package com.robertx22.age_of_exile.capability.world;

import nerdhub.cardinal.components.api.util.sync.ChunkSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.chunk.Chunk;

public class ChunkAreaCap implements ChunkSyncedComponent {

    public ChunkAreaCap(Chunk chunk) {
        this.chunk = chunk;
    }

    Chunk chunk;
    public String uuid = "";

    public boolean isInit() {
        return !uuid.isEmpty();
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.uuid = nbt.getString("uuid");
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        nbt.putString(uuid, "uuid");
        return nbt;
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }
}
