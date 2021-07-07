package com.robertx22.age_of_exile.dimension.rift;

import com.robertx22.age_of_exile.dimension.SpawnUtil;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

public class SummonPortalBlockEntity extends BlockEntity implements Tickable {

    public SummonPortalBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.SUMMON_PORTAL);
    }

    public SummonPortalData data = new SummonPortalData();

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
            this.data = LoadSave.Load(SummonPortalData.class, new SummonPortalData(), nbt, "rift");

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

            if (data.summons_left < 1) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                return;
            }

            ticks++;

            if (ticks % 100 == 0) {
                DungeonData data = Load.dungeonData(world).data.get(pos).data;

                if (!data.failedOrEmpty()) {

                    EntityType type = data.getMobList()
                        .getRandomMob();

                    for (int i = 0; i < 50; i++) {
                        int ymin = -3;
                        int ymax = 3;

                        int distanceMin = -5;
                        int distanceMax = 5;

                        int x = RandomUtils.RandomRange(distanceMin, distanceMax);
                        int y = RandomUtils.RandomRange(ymin, ymax);
                        int z = RandomUtils.RandomRange(distanceMin, distanceMax);

                        BlockPos randomPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

                        if (SpawnUtil.canPlaceMob(world, type, randomPos)) {

                            for (int s = 0; s < 5; s++) {
                                BlockPos check = new BlockPos(randomPos.getX(), randomPos.getY() - s, randomPos.getZ());
                                if (SpawnUtil.canPlaceMob(world, type, check)) {
                                    randomPos = check;
                                } else {
                                    break;
                                }
                            }

                            this.data.summons_left--;
                            data.getMobList()
                                .spawnMob((ServerWorld) world, type, randomPos);
                            break;
                        }

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

