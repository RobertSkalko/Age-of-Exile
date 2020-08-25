package com.robertx22.age_of_exile.mobs.spiders;

import com.robertx22.age_of_exile.mobs.OnTickRandomSpeedBoost;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BaseSpider extends SpiderEntity {

    public BaseSpider(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        OnTickRandomSpeedBoost.onTick(this, 60, 50);
        super.tick();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    @Override
    protected Identifier getLootTableId() {
        return EntityType.SPIDER.getLootTableId(); // TODO, add loot tables later
    }

}


