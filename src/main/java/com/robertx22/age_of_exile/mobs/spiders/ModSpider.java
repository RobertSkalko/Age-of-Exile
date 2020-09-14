package com.robertx22.age_of_exile.mobs.spiders;

import com.robertx22.age_of_exile.mobs.OnTickRandomSpeedBoost;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModSpider extends SpiderEntity {

    public ModSpider(EntityType<? extends SpiderEntity> entityType, World world) {
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

    boolean warnedOthers = false;

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getHealth() >= this.getMaxHealth() * 0.9F) {
            Entity attacker = source.getAttacker();
            if (attacker instanceof LivingEntity) {
                if (attacker != getAttacker()) {
                    if (!warnedOthers) {
                        OnTickRandomSpeedBoost.onAttackedAngerNearbyMobs(this, (LivingEntity) attacker);
                        warnedOthers = true;
                    }
                }
            }
        }
        return super.damage(source, amount);
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
        return EntityType.SPIDER.getLootTableId(); // TODO, add loot tables later
    }

}


