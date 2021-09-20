package com.robertx22.age_of_exile.dimension.spawner;

import com.robertx22.age_of_exile.dimension.SpawnUtil;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class ModSpawnerBlockEntity extends TileEntity implements ITickableTileEntity {

    public static int DEFAULT_SPAWNS = 10;

    private static int MAX_NEARBY_ENTITIES = 5;
    private static int spawnRange = 16;

    public ModSpawnerBlockEntity() {
        super(SlashBlockEntities.SPAWNER.get());
    }

    int ticks = 0;
    public int spawnsLeft = DEFAULT_SPAWNS;
    public boolean spawnAllAtOnce = false;
    public int requiredPlayerRange = 16;

    @Override
    public void tick() {

        if (spawnsLeft < 1) {
            if (!level.isClientSide) {
                this.level.removeBlock(worldPosition, false);
                return;
            }
        }

        ticks++;

        if (ticks % 60 == 0) {
            if (!level.isClientSide) {
                if (isPlayerInRange()) {
                    if (WorldUtils.isMapWorldClass(level)) {
                        int entities = level.getEntitiesOfClass(LivingEntity.class, (new AxisAlignedBB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), (worldPosition.getX() + 1), (worldPosition.getY() + 1), (worldPosition.getZ() + 1))).inflate(this.spawnRange))
                            .size();
                        if (!spawnAllAtOnce && entities > MAX_NEARBY_ENTITIES) {
                            return;
                        }

                        DungeonMobList list = Load.dungeonData(level).data.get(worldPosition).data.getMobList();

                        if (list != null) {
                            int spawns = RandomUtils.RandomRange(1, 4);

                            if (spawns > spawnsLeft || spawnAllAtOnce) {
                                spawns = spawnsLeft;
                            }

                            this.spawnsLeft -= spawns;

                            List<BlockPos> positions = new ArrayList<>();
                            for (int x = -4; x < 4; x++) {
                                for (int z = -4; z < 4; z++) {
                                    positions.add(worldPosition.offset(x, 0, z));
                                    positions.add(worldPosition.offset(x, 1, z));
                                }
                            }
                            ParticleEnum.sendToClients(worldPosition, level,
                                new ParticlePacketData(worldPosition, ParticleEnum.AOE).radius(0.5F)
                                    .type(ParticleTypes.FLAME)
                                    .motion(new Vector3d(0, 0, 0))
                                    .amount(10));

                            ParticleEnum.sendToClients(worldPosition, level,
                                new ParticlePacketData(worldPosition, ParticleEnum.AOE).radius(0.5F)
                                    .type(ParticleTypes.SMOKE)
                                    .motion(new Vector3d(0, 0, 0))
                                    .amount(10));

                            for (int i = 0; i < spawns; i++) {

                                while (spawns > 0 && !positions.isEmpty()) {

                                    BlockPos blockPos = RandomUtils.randomFromList(positions);

                                    EntityType type = list.getRandomMob();

                                    if (SpawnUtil.canPlaceMob(level, type, blockPos)) {
                                        list.spawnMob((ServerWorld) level, type, blockPos);
                                        spawns--;
                                    }

                                    positions.remove(blockPos);
                                }

                            }
                        }

                    }
                }
            }
        }

    }

    private boolean isPlayerInRange() {
        BlockPos blockPos = this.getBlockPos();

        return this.getLevel()
            .hasNearbyAlivePlayer((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, (double) this.requiredPlayerRange);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("ticks", ticks);
        nbt.putInt("spawnsLeft", spawnsLeft);
        nbt.putInt("range", requiredPlayerRange);
        nbt.putBoolean("at_once", spawnAllAtOnce);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        try {
            super.load(state, nbt);
            ticks = nbt.getInt("ticks");
            spawnsLeft = nbt.getInt("spawnsLeft");
            requiredPlayerRange = nbt.getInt("range");
            spawnAllAtOnce = nbt.getBoolean("at_once");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
