package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.addon.divine_missions.types.CompleteDungeonTask;
import com.robertx22.age_of_exile.capability.PlayerDamageChart;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.DungeonDimensionJigsawFeature;
import com.robertx22.age_of_exile.dimension.PopulateDungeonChunks;
import com.robertx22.age_of_exile.dimension.dungeon_data.*;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SignUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.divine_missions.mission_gen.MissionUtil;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.Watch;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.*;

@Storable
public class MapsData {

    public static Block TELEPORT_TO_PLACEHOLDER_BLOCK = Blocks.PLAYER_HEAD;
    static int cachedBorder = 0;

    @Store
    public int ticksinPortal = 0;
    @Store
    public int highestTierDone = 0;

    @Store
    public DungeonData dungeonData = new DungeonData();
    @Store
    public BlockPos tel_pos = new BlockPos(0, 0, 0);
    @Store
    public String tp_b_dim = "";
    @Store
    public boolean isEmpty = true;
    @Store
    public String orig_gamemode = "";

    static int getBorder(World world) {
        if (cachedBorder == 0) {
            cachedBorder = (int) (world.getWorldBorder()
                .getSize() / 2);
        }
        return cachedBorder;
    }

    public void onDungeonCompletedAdvanceProgress(PlayerEntity player) {

        TeamUtils.getOnlineMembers(player)
            .forEach(e -> {
                MissionUtil.tryDoMissions(e, x -> {
                    if (x.getTaskEntry().task_type.equals(CompleteDungeonTask.ID)) {
                        x.increaseProgress();
                        return;
                    }
                });
            });

        int tier = 0;
        try {
            tier = dungeonData
                .getDifficulty().rank;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tier > highestTierDone) {
            highestTierDone = tier;
        }

    }

    public void onStartDungeon(PlayerEntity player, TeamSize teamSize, BlockPos teleporterPos, String uuid) {

        try {

            TeamUtils.getOnlineTeamMembersInRange(player)
                .forEach(x -> PlayerDamageChart.clear(x));

            Watch total = new Watch();

            Watch first = new Watch();

            tel_pos = teleporterPos;

            // set the dungeon data for the chunk
            World dimWorld = player.level.getServer()
                .getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, dungeonData.dun_type.DIMENSION_ID));

            int border = MapsData.getBorder(dimWorld);

            int X = RandomUtils.RandomRange(-border, border);
            int Z = RandomUtils.RandomRange(-border, border);

            ChunkPos cp = DungeonDimensionJigsawFeature.getSpawnChunkOf(new BlockPos(X, 0, Z));

            Watch ww = new Watch();

            //Chunk chunk = dimWorld.getChunk(cp.x, cp.z); // load it first

            ww.print("loading first chunk ");

            BlockPos tpPos = cp.getWorldPosition()
                .offset(8, DungeonDimensionJigsawFeature.HEIGHT, 8); // todo, seems to not point to correct location?

            first.print("first part ");

            String moblist = "";

            if (true) {

                Watch w = new Watch();

                List<ChunkPos> check = PopulateDungeonChunks.getChunksAround(cp);

                boolean foundSpawn = false;
                boolean foundportalback = false;

                for (ChunkPos x : check) {

                    Set<Map.Entry<BlockPos, TileEntity>> bes = new HashSet<>(dimWorld.getChunk(x.x, x.z)
                        .getBlockEntities()
                        .entrySet());

                    for (Map.Entry<BlockPos, TileEntity> e : bes) {

                        if (e.getValue() instanceof SignTileEntity) {
                            if (SignUtils.has("[moblist]", (SignTileEntity) e.getValue())) {
                                moblist = SignUtils.removeBraces(SignUtils.getText((SignTileEntity) e.getValue())
                                    .get(1));
                            }
                            if (SignUtils.has("[portal]", (SignTileEntity) e.getValue())) {
                                dimWorld.setBlockAndUpdate(e.getKey(), SlashBlocks.PORTAL.get()
                                    .defaultBlockState());
                                foundportalback = true;
                            }
                        } else if (dimWorld.getBlockState(e.getKey())
                            .getBlock() == MapsData.TELEPORT_TO_PLACEHOLDER_BLOCK) {
                            tpPos = e.getKey();
                            dimWorld.destroyBlock(tpPos, false);
                            foundSpawn = true;
                        }
                    }
                }

                if (!foundSpawn) {
                    player.displayClientMessage(new StringTextComponent("Bug: Couldnt find spawn position, you might be placed weirdly, and possibly die."), false);
                }
                if (!foundportalback) {
                    player.displayClientMessage(new StringTextComponent("Bug: Couldnt find portal back position."), false);
                }

                w.print("finding spawn");

            }

            List<BlockPos> list = new ArrayList<>();
            list.add(teleporterPos.south(2));
            list.add(teleporterPos.north(2));
            list.add(teleporterPos.east(2));
            list.add(teleporterPos.west(2));

            for (BlockPos x : list) {
                if (player.level.getBlockState(x)
                    .isAir() || player.level.getBlockState(x)
                    .getBlock() == SlashBlocks.PORTAL.get()) {
                    player.level.destroyBlock(x, false);
                    player.level.setBlockAndUpdate(x, SlashBlocks.PORTAL.get()
                        .defaultBlockState());
                    PortalBlockEntity be = (PortalBlockEntity) player.level.getBlockEntity(x);
                    be.data.dungeonPos = tpPos;
                    be.data.tpbackpos = teleporterPos.above();
                    be.data.dungeonType = dungeonData.dun_type;

                }
            }

            // first set the data so the mob levels can be set on spawn
            WorldDungeonCap data = Load.dungeonData(dimWorld);
            SingleDungeonData single = new SingleDungeonData(dungeonData, new QuestProgression(dungeonData.uuid, 20), player.getUUID()
                .toString());
            if (ExileDB.DungeonMobLists()
                .isRegistered(moblist)) {
                single.data.setMobList(moblist);
            }
            data.data.set(player, tpPos, single);

            single.data.team = teamSize;

            single.pop.startPopulating(cp);

            total.print("Total dungeon init");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // todo add to RPG CAP

}
