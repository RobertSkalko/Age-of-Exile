package com.robertx22.age_of_exile.mobs.zombies;

import com.robertx22.age_of_exile.mobs.OnTickRandomSpeedBoost;
import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModZombie extends ZombieEntity {

    public ModZombie(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);

    }

    int angryTicks = 0;

    @Override
    public void tick() {
        if (OnTickRandomSpeedBoost.onTickTryAnger(this, angryTicks)) {
            angryTicks = 100;
        }
        angryTicks--;
        super.tick();
    }

    @Override
    protected void initGoals() {
        //this.goalSelector.add(4, new GriefBlocksGoal(Blocks.TORCH, this, 1.0D, 6)); // TODO REMOVE
        super.initGoals();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

    @Override
    protected void initAttributes() {
        this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
            .setBaseValue(0);
    }

    @Override
    public boolean isConvertingInWater() {
        return false;
    }

    @Override
    protected void convertInWater() {
    }

    @Override
    protected Identifier getLootTableId() {
        return EntityType.ZOMBIE.getLootTableId(); // TODO, add loot tables later
    }

}



