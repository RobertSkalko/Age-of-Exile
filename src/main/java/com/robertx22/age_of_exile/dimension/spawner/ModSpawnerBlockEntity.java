package com.robertx22.age_of_exile.dimension.spawner;

import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class ModSpawnerBlockEntity extends BlockEntity implements Tickable {

    public static int DEFAULT_SPAWNS = 10;

    private static int maxNearbyEntities = 6;
    private static int requiredPlayerRange = 16;
    private static int spawnRange = 4;

    public ModSpawnerBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.SPAWNER);
    }

    int ticks = 0;
    int spawnsLeft = DEFAULT_SPAWNS;

    @Override
    public void tick() {

        ticks++;

        if (ticks % 140 == 0) {
            if (!world.isClient) {
                if (isPlayerInRange()) {
                    if (WorldUtils.isDungeonWorld(world)) {
                        int entities = world.getNonSpectatingEntities(LivingEntity.class, (new Box(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1))).expand(this.spawnRange))
                            .size();
                        if (entities < this.maxNearbyEntities) {

                            DungeonMobList list = Load.dungeonData(world).data.get(pos).data.getMobList();

                            if (list != null) {
                                int spawns = RandomUtils.RandomRange(1, 4);

                                if (spawns > spawnsLeft) {
                                    spawns = spawnsLeft;
                                }

                                List<BlockPos> positions = new ArrayList<>();
                                for (int x = -4; x < 4; x++) {
                                    for (int z = -4; z < 4; z++) {
                                        positions.add(pos.add(x, 0, z));
                                        positions.add(pos.add(x, 1, z));
                                    }
                                }
                                ParticleEnum.sendToClients(pos, world,
                                    new ParticlePacketData(pos, ParticleEnum.AOE).radius(0.5F)
                                        .type(ParticleTypes.FLAME)
                                        .motion(new Vec3d(0, 0, 0))
                                        .amount(10));

                                ParticleEnum.sendToClients(pos, world,
                                    new ParticlePacketData(pos, ParticleEnum.AOE).radius(0.5F)
                                        .type(ParticleTypes.SMOKE)
                                        .motion(new Vec3d(0, 0, 0))
                                        .amount(10));

                                for (int i = 0; i < spawns; i++) {

                                    while (spawns > 0 && !positions.isEmpty()) {

                                        BlockPos blockPos = RandomUtils.randomFromList(positions);

                                        if (world.getBlockState(blockPos.down())
                                            .isSolidBlock(world, blockPos.down()) && world.isAir(blockPos) && world.isAir(blockPos.up())) {

                                            list.spawnRandomMob((ServerWorld) world, blockPos, 0);
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
    }

    private boolean isPlayerInRange() {
        BlockPos blockPos = this.getPos();
        return this.getWorld()
            .isPlayerInRange((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, (double) this.requiredPlayerRange);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        nbt.putInt("ticks", ticks);
        nbt.putInt("spawnsLeft", spawnsLeft);
        return nbt;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag nbt) {
        try {
            super.fromTag(state, nbt);
            ticks = nbt.getInt("ticks");
            spawnsLeft = nbt.getInt("spawnsLeft");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
