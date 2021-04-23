package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsawFeature;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.teleporter.MapsData;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

public class PlayerMapsCap implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "player_maps");
    private static final String LOC = "maps";

    PlayerEntity player;

    public MapsData mapsData = new MapsData();

    MapsPathingData data = new MapsPathingData();

    public PlayerMapsCap(PlayerEntity player) {
        this.player = player;
    }

    public void onDungeonCompletedAdvanceProgress() {
        data.floor++;
    }

    public void onEnterDungeon(BlockPos teleporterPos, String uuid) {

        try {
            ImmutablePair<Integer, DungeonData> pair = getDungeonFromUUID(uuid);

            this.data.entered.add(new PathData(pair.right.uuid, pair.left));

            this.data.tel_pos = teleporterPos;

            List<BlockPos> list = new ArrayList<>();
            list.add(teleporterPos.south(2));
            list.add(teleporterPos.north(2));
            list.add(teleporterPos.east(2));
            list.add(teleporterPos.west(2));

            int border = (int) (player.world.getWorldBorder()
                .getSize() / 2);

            int X = RandomUtils.RandomRange(-border, border);
            int Z = RandomUtils.RandomRange(-border, border);

            ChunkPos cp = DungeonDimensionJigsawFeature.getSpawnChunkOf(new BlockPos(X, 0, Z));

            BlockPos tpPos = cp.getStartPos()
                .add(8, DungeonDimensionJigsawFeature.HEIGHT, 8); // todo, seems to not point to correct location?

            list.forEach(x -> {
                if (player.world.getBlockState(x)
                    .isAir()) {
                    player.world.setBlockState(x, ModRegistry.BLOCKS.PORTAL.getDefaultState());
                    PortalBlockEntity be = (PortalBlockEntity) player.world.getBlockEntity(x);
                    be.dungeonPos = tpPos;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ImmutablePair<Integer, DungeonData> getDungeonFromUUID(String uuid) {

        for (Map.Entry<Integer, List<DungeonData>> x : mapsData.dungeonsByFloors.entrySet()) {
            Optional<DungeonData> opt = x.getValue()
                .stream()
                .filter(e -> e.uuid.equals(uuid))
                .findAny();

            if (opt.isPresent()) {
                return ImmutablePair.of(x.getKey(), opt.get());
            }
        }
        return null;
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(mapsData, nbt, LOC);
        LoadSave.Save(data, nbt, "path");
        return nbt;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.mapsData = LoadSave.Load(MapsData.class, new MapsData(), nbt, LOC);
        this.data = LoadSave.Load(MapsPathingData.class, new MapsPathingData(), nbt, "path");

        if (data == null) {
            data = new MapsPathingData();
        }
        if (mapsData == null) {
            mapsData = new MapsData();
        }
    }

    public boolean isLockedToPlayer(DungeonData dungeon) {

        Optional<Map.Entry<Integer, List<DungeonData>>> opt = this.mapsData.dungeonsByFloors.entrySet()
            .stream()
            .filter(x -> x.getValue()
                .stream()
                .anyMatch(e -> e.uuid.equals(dungeon.uuid)))
            .findFirst();

        if (opt.isPresent()) {

            int floor = opt.get()
                .getKey();

            if (floor != this.data.floor) {
                return true;
            }

            if (data.enteredAnotherDungeonOnSameFloor(dungeon, floor)) {
                return true;
            }

            return false;

        }
        return true;

    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.MAPS;
    }

    public void initRandomMap(int tier) {

        this.data.floor = 0;
        this.mapsData = new MapsData();

        mapsData.isEmpty = false;

        int floors = RandomUtils.RandomRange(5, 5);

        for (int f = 0; f < floors; f++) {

            int perFloor = RandomUtils.RandomRange(1, 3);

            for (int d = 0; d < perFloor; d++) {

                boolean isEndOfMap = f == 4;

                List<DungeonData> list = mapsData.dungeonsByFloors.getOrDefault(f, new ArrayList<>());

                DungeonData dun = new DungeonData();

                int dungeonTier = f + tier;

                if (dungeonTier > ITiered.getMaxTier()) {
                    dungeonTier = ITiered.getMaxTier();
                }

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
                dun.quest_rew.randomize(dungeonTier, isEndOfMap);

                list.add(dun);

                mapsData.dungeonsByFloors.put(f, list);
            }

        }

    }
}
