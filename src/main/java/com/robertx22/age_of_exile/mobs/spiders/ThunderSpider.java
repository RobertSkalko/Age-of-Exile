package com.robertx22.age_of_exile.mobs.spiders;

import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ThunderSpider extends SpiderEntity {

    public ThunderSpider(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

}


