package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsawFeature;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

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

    public void onTick(World world) {
        map.values()
            .forEach(x -> x.pop.onTick(world, x));
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

            //allow world to save 10 dungeon datas for each player.
            // Means there will never be too much data to slow down servers when loading
            // and there's enough so players can't just spam dungeons to clear the data if someone
            // pvp kills them in rifts

            map.entrySet()
                .forEach(x -> {
                    if (x.getValue().ownerUUID.equals(playerUUID)) {
                        x.getValue().data_lives--;
                    }
                });
            map.entrySet()
                .removeIf(x -> x.getValue().data_lives < 1 && x.getValue().ownerUUID.equals(playerUUID)); // we dont want an infinitely expanding world data

            map.put(key, data);
        }
    }
}
