package com.robertx22.age_of_exile.mobs.slimes;

import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ModSlime extends MagmaCubeEntity {

    public ModSlime(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);

    }

    @Override
    public final Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    public boolean canSpawn(WorldView world) {
        return super.canSpawn(world) && world.getBrightness(this.getBlockPos()) < 0.5F;
    }

    @Override
    protected void setSize(int size, boolean heal) {
        super.setSize(MathHelper.clamp(size, 0, 5), heal);
    }

}


