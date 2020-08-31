package com.robertx22.age_of_exile.mobs.skeletons;

import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BaseSkeleton extends SkeletonEntity {

    public BaseSkeleton(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    /*
    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

     */

    @Override
    protected Identifier getLootTableId() {
        return EntityType.SKELETON.getLootTableId(); // TODO, add loot tables later
    }

    @Override
    protected boolean isInDaylight() {
        return false;
    }
}
