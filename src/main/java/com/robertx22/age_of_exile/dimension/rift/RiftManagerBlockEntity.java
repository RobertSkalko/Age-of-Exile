package com.robertx22.age_of_exile.dimension.rift;

import com.robertx22.age_of_exile.dimension.PopulateDungeonChunks;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.divine_missions.mission_gen.MissionUtil;
import com.robertx22.divine_missions_addon.types.CompleteRiftTask;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

public class RiftManagerBlockEntity extends BlockEntity implements Tickable {

    public RiftManagerBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.RIFT_MANAGER);
    }

    public RiftData data = new RiftData();

    int ticks = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (data != null) {
            LoadSave.Save(data, nbt, "rift");
        }
        return nbt;
    }

    @Override
    public void fromTag(BlockState state, NbtCompound nbt) {
        try {
            super.fromTag(state, nbt);
            this.data = LoadSave.Load(RiftData.class, new RiftData(), nbt, "rift");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {

        try {
            if (world.isClient) {
                return;
            }

            data.ticks_till_next_wave--;

            ticks++;

            if (data.wave_num == 0) {
                int secTillWave = data.ticks_till_next_wave / 20;

                TeamUtils.forEachMember(world, pos, x -> {
                    OnScreenMessageUtils.sendMessage(
                        (ServerPlayerEntity) x,
                        new LiteralText("Starting in " + secTillWave + "s").formatted(Formatting.YELLOW),
                        TitleS2CPacket.Action.ACTIONBAR);
                });
            }

            if (data.ticks_till_next_wave < 1) {

                data.ticks_till_next_wave = 20 * 60;
                data.wave_num++;

                DungeonData dungeonData = Load.dungeonData(world).data.get(pos).data;

                if (dungeonData.fail) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    return;
                }

                if (data.wave_num >= data.max_waves) {
                    // todo spawn rewards here at last wave

                    TeamUtils.forEachMember(world, pos, x -> {

                        MissionUtil.tryDoMissions(x, e -> {
                            if (e.getTaskEntry().task_type.equals(CompleteRiftTask.ID)) {
                                e.increaseProgress();
                                return;
                            }
                        });

                        OnScreenMessageUtils.sendMessage(
                            (ServerPlayerEntity) x,
                            new LiteralText("Rift Cleared.").formatted(Formatting.GREEN),
                            TitleS2CPacket.Action.TITLE);
                    });

                    int chests = RandomUtils.RandomRange(1, 2);

                    for (int i = 0; i < chests; i++) {
                        summonEndRewardChests();
                    }

                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    return;
                } else {
                    TeamUtils.forEachMember(world, pos, x -> {
                        OnScreenMessageUtils.sendMessage(
                            (ServerPlayerEntity) x,
                            new LiteralText("Wave " + (data.wave_num)).formatted(Formatting.DARK_PURPLE),
                            TitleS2CPacket.Action.TITLE);
                    });

                    if (!dungeonData.failedOrEmpty()) {
                        for (int i = 0; i < 10; i++) {
                            createMobSummonPortal();
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void summonEndRewardChests() {

        for (int i = 0; i < 500; i++) {
            int ymin = -25;
            int ymax = 25;

            int distanceMin = -5;
            int distanceMax = 5;

            int x = RandomUtils.RandomRange(distanceMin, distanceMax);
            int y = RandomUtils.RandomRange(ymin, ymax);
            int z = RandomUtils.RandomRange(distanceMin, distanceMax);

            BlockPos randomPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

            if (world.isAir(randomPos)) {

                for (int s = 0; s < 10; s++) {
                    BlockPos check = new BlockPos(randomPos.getX(), randomPos.getY() - s, randomPos.getZ());
                    if (world.isAir(check)) {
                        randomPos = check;
                    } else {
                        break;
                    }
                }
                PopulateDungeonChunks.setChest(world, randomPos);
                break;
            }

        }

    }

    void createMobSummonPortal() {

        for (int i = 0; i < 500; i++) {
            int ymin = -30;
            int ymax = 30;

            int distanceMin = -16 * 4;
            int distanceMax = 16 * 4;

            int x = RandomUtils.RandomRange(distanceMin, distanceMax);
            int y = RandomUtils.RandomRange(ymin, ymax);
            int z = RandomUtils.RandomRange(distanceMin, distanceMax);

            BlockPos randomPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

            if (world.isAir(randomPos)) {

                for (int s = 0; s < 5; s++) {
                    BlockPos check = new BlockPos(randomPos.getX(), randomPos.getY() - s, randomPos.getZ());
                    if (world.isAir(check)) {
                        randomPos = check;
                    } else {
                        break;
                    }
                }

                world.setBlockState(randomPos, ModRegistry.BLOCKS.SUMMON_PORTAL.getDefaultState());
                break;
            }

        }

    }
}
