package com.robertx22.age_of_exile.mobs.skeletons;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModSkeleton extends SkeletonEntity {

    public ModSkeleton(EntityType<? extends SkeletonEntity> entityType, World world) {
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

}
