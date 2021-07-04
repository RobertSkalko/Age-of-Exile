package com.robertx22.age_of_exile.dimension.rift;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

            if (true) {
                return;
            }

            ticks++;

            if (ticks % 100 == 0) {

                DungeonData data = Load.dungeonData(world).data.get(pos).data;

                if (MMORPG.RUN_DEV_TOOLS) {
                    data.uuid = "sddss";
                }

                if (!data.failedOrEmpty()) {

                    for (int i = 0; i < 50; i++) {
                        int ymin = -30;
                        int ymax = 30;

                        int distanceMin = -16 * 4;
                        int distanceMax = 16 * 4;

                        int x = RandomUtils.RandomRange(distanceMin, distanceMax);
                        int y = RandomUtils.RandomRange(ymin, ymax);
                        int z = RandomUtils.RandomRange(distanceMin, distanceMax);

                        BlockPos randomPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

                        if (world.isAir(randomPos)) {
                            data.getMobList()
                                .spawnMob((ServerWorld) world, data.getMobList()
                                    .getRandomMob(), randomPos);

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
