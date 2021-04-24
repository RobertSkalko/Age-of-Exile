package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsawFeature;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.QuestProgression;
import com.robertx22.age_of_exile.dimension.dungeon_data.SingleDungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.dimension.teleporter.MapsData;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
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

    public static Block TELEPORT_TO_PLACEHOLDER_BLOCK = Blocks.PLAYER_HEAD;

    static int cachedBorder = 0;

    static int getBorder(World world) {
        if (cachedBorder == 0) {
            cachedBorder = (int) (world.getWorldBorder()
                .getSize() / 2);
        }
        return cachedBorder;
    }

    public void onEnterDungeon(BlockPos teleporterPos, String uuid) {

        try {

            Watch total = new Watch();

            ImmutablePair<Integer, DungeonData> pair = getDungeonFromUUID(uuid);

            this.data.entered.add(new PathData(pair.right.uuid, pair.left));

            this.data.tel_pos = teleporterPos;

            // set the dungeon data for the chunk
            World dimWorld = player.world.getServer()
                .getWorld(RegistryKey.of(Registry.DIMENSION, DimensionIds.DUNGEON_DIMENSION));

            int border = getBorder(dimWorld);

            int X = RandomUtils.RandomRange(-border, border);
            int Z = RandomUtils.RandomRange(-border, border);

            ChunkPos cp = DungeonDimensionJigsawFeature.getSpawnChunkOf(new BlockPos(X, 0, Z));

            Chunk chunk = dimWorld.getChunk(cp.x, cp.z); // load it first

            BlockPos tpPos = cp.getStartPos()
                .add(8, DungeonDimensionJigsawFeature.HEIGHT, 8); // todo, seems to not point to correct location?

            List<BlockPos> list = new ArrayList<>();
            list.add(teleporterPos.south(2));
            list.add(teleporterPos.north(2));
            list.add(teleporterPos.east(2));
            list.add(teleporterPos.west(2));

            for (BlockPos x : list) {
                if (player.world.getBlockState(x)
                    .isAir()) {
                    player.world.setBlockState(x, ModRegistry.BLOCKS.PORTAL.getDefaultState());
                    PortalBlockEntity be = (PortalBlockEntity) player.world.getBlockEntity(x);
                    be.dungeonPos = tpPos;
                }
            }

            if (true) {

                Watch w = new Watch();

                Set<ChunkPos> check = new HashSet<>();
                check.add(cp);
                for (int x = -2; x < 2; x++) {
                    for (int z = -2; z < 2; z++) {
                        check.add(new ChunkPos(cp.x, cp.z + z));
                    }
                }

                for (ChunkPos x : check) {
                    for (Map.Entry<BlockPos, BlockEntity> e : dimWorld.getChunk(x.x, x.z)
                        .getBlockEntities()
                        .entrySet()) {
                        if (e.getValue() instanceof SkullBlockEntity && dimWorld.getBlockState(e.getKey())
                            .getBlock() == TELEPORT_TO_PLACEHOLDER_BLOCK) {
                            tpPos = e.getKey();
                            break;
                        }
                    }
                }

                w.print("finding spawn");

            }

            WorldDungeonCap data = Load.dungeonData(dimWorld);

            DungeonPopulateData popData = new DungeonPopulateData();

            Watch watch = new Watch();
            populateAround(dimWorld, cp, pair.right, popData);
            watch.print("init dungeon");

            int kills = (int) (popData.mobs * 0.8F);

            SingleDungeonData single = new SingleDungeonData(pair.right, new QuestProgression(pair.right.uuid, kills), player.getUuid()
                .toString());

            data.data.set(player, tpPos, single);

            total.print("Total dungeon init");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class DungeonPopulateData {

        public int mobs = 0;
        public int chests = 0;
    }

    public void populateAround(World world, ChunkPos cp, DungeonData dungeon, DungeonPopulateData data) {
        Set<ChunkPos> chunks = new HashSet<>();
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                chunks.add(new ChunkPos(cp.x + x, cp.z + z));
            }
        }
        chunks.forEach(x -> populate(world, world.getChunk(x.x, x.z), dungeon, data));
    }

    public void populate(World world, Chunk chunk, DungeonData dungeon, DungeonPopulateData data) {

        Set<BlockPos> list = chunk.getBlockEntityPositions();

        list.forEach(x -> {
            BlockEntity be = world.getBlockEntity(x);
            if (be instanceof BeaconBlockEntity) {
                populate(world, x, dungeon, data);
            }
        });

    }

    void populate(World world, BlockPos pos, DungeonData dungeonData, DungeonPopulateData data) {

        List<BlockPos> list = new ArrayList<>();
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                list.add(pos.add(x, 0, z));
            }
        }

        //list.removeIf(x -> !world.isAir(x) && !world.getBlockState(x.down())
        //  .isSolidBlock(world, x.down()));

        int mobs = RandomUtils.RandomRange(2, 5);
        int chests = RandomUtils.roll(10) ? 1 : 0;

        int tries = 0;
        for (int i = 0; i < mobs; i++) {
            BlockPos p = RandomUtils.randomFromList(list);

            tries++;
            if (tries > 50) {
                break;
            }
            if (!world.isAir(p) && !world.getBlockState(p.down())
                .isSolidBlock(world, p.down())) {
                i--;
                continue;
            }
            data.mobs++;
            dungeonData.getMobList()
                .spawnRandomMob((ServerWorld) world, p, dungeonData.tier);
            list.remove(p);
        }

        tries = 0;

        for (int i = 0; i < chests; i++) {
            BlockPos p = RandomUtils.randomFromList(list);
            tries++;
            if (tries > 50) {
                break;
            }
            if (!world.isAir(p) && !world.getBlockState(p.down())
                .isSolidBlock(world, p.down())) {
                i--;
                continue;
            }
            data.chests++;
            world.setBlockState(p, Blocks.CHEST.getDefaultState()); // TODO ADD LOOT
            list.remove(p);
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState());

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
