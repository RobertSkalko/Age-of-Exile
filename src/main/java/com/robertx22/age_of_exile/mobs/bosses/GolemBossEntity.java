package com.robertx22.age_of_exile.mobs.bosses;

import com.robertx22.age_of_exile.mobs.bosses.bases.BossData;
import com.robertx22.age_of_exile.mobs.bosses.bases.IBossMob;
import com.robertx22.age_of_exile.mobs.bosses.channels.AngerChannel;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Arrays;

public class GolemBossEntity extends IronGolemEntity implements IBossMob {

    public BossData bossdata = new BossData(this, Arrays.asList(new AngerChannel(this)));

    public GolemBossEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean shouldAngerAt(LivingEntity entity) {
        return entity instanceof PlayerEntity;
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
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6D, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6D));
        this.goalSelector.add(5, new IronGolemLookGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 20));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));

    }

    @Override
    public void tick() {
        super.tick();

        this.bossdata.onTick();

    }

    @Override
    public BossData getBossData() {
        return bossdata;
    }
}


