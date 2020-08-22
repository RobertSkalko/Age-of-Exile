package com.robertx22.age_of_exile.mobs.slimes;

import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BaseSlime extends MagmaCubeEntity {

    public BaseSlime(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);

    }

    @Override
    public final Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected void setSize(int size, boolean heal) {
        super.setSize(MathHelper.clamp(size, 0, 5), heal);
    }

}


