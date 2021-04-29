package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsawFeature;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;

@Storable
public class WorldDungeonsData {

    @Store
    private HashMap<String, SingleDungeonData> map = new HashMap<>();

    String keyOf(BlockPos pos) {
        ChunkPos cp = DungeonDimensionJigsawFeature.getSpawnChunkOf(pos);
        String key = cp.x + "_" + cp.z;
        return key;

    }

    public SingleDungeonData get(BlockPos pos) {
        String key = keyOf(pos);
        SingleDungeonData data = map.getOrDefault(key, SingleDungeonData.EMPTY);
        return data;
    }

    public void set(PlayerEntity player, BlockPos pos, SingleDungeonData data) {

        String key = keyOf(pos);
        String playerUUID = player.getUuid()
            .toString();

        if (!map.containsKey(key)) {
            map.entrySet()
                .removeIf(x -> x.getValue().ownerUUID.equals(playerUUID)); // we dont want an infinitely expanding world data

            map.put(key, data);
        }
    }
}
