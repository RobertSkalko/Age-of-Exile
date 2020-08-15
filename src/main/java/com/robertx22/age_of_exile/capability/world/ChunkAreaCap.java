package com.robertx22.age_of_exile.capability.world;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import nerdhub.cardinal.components.api.util.sync.ChunkSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.UUID;

public class ChunkAreaCap implements ChunkSyncedComponent {

    public ChunkAreaCap(Chunk chunk) {
        this.chunk = chunk;
    }

    public static ChunkAreaCap getArea(World world, BlockPos pos) {
        ChunkAreaCap area = ModRegistry.COMPONENTS.CHUNK_AREA.get(world.getChunk(pos));

        if (!area.isInit()) {

            area.uuid = UUID.randomUUID()
                .toString(); // JUST FOR TESTING CHUNK COMPONENT

        }

        return area;
    }

    Chunk chunk;
    String uuid = "";

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
