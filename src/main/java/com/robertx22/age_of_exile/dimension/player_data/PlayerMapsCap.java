package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.teleporter.MapsData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerMapsCap implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "player_maps");
    private static final String LOC = "maps";

    PlayerEntity player;

    public MapsData mapsData = new MapsData();

    public int currentFloor = 0;

    public PlayerMapsCap(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        nbt.putInt("floor", currentFloor);
        LoadSave.Save(mapsData, nbt, LOC);
        return nbt;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.mapsData = LoadSave.Load(MapsData.class, new MapsData(), nbt, LOC);
        this.currentFloor = nbt.getInt("floor");
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.MAPS;
    }

    public void initRandomMap(int tier) {

        this.currentFloor = 0;
        this.mapsData = new MapsData();

        mapsData.isEmpty = false;

        int floors = RandomUtils.RandomRange(5, 5);

        for (int f = 0; f < floors; f++) {

            int perFloor = RandomUtils.RandomRange(1, 3);

            for (int d = 0; d < perFloor; d++) {

                List<DungeonData> list = mapsData.dungeonsByFloors.getOrDefault(f, new ArrayList<>());

                DungeonData dun = new DungeonData();

                int dungeonTier = f + tier;

                dun.lvl = Load.Unit(player)
                    .getLevel();
                dun.tier = dungeonTier;
                dun.mob_list = Database.DungeonMobLists()
                    .random()
                    .GUID();
                dun.uuid = UUID.randomUUID()
                    .toString();
                dun.uniques.randomize(dungeonTier);
                dun.affixes.randomize(dungeonTier);

                list.add(dun);

                mapsData.dungeonsByFloors.put(f, list);
            }

        }

    }
}
