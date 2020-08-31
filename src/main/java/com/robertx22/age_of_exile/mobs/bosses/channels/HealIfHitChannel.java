package com.robertx22.age_of_exile.mobs.bosses.channels;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.mobs.bosses.bases.ChannelAction;
import com.robertx22.age_of_exile.mobs.bosses.bases.IBossMob;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class HealIfHitChannel<T extends LivingEntity & IBossMob> extends ChannelAction<T> {

    public HealIfHitChannel(T en) {
        super(en);
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onTick() {

    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.GOOD_FOR_BOSS;
    }

    @Override
    public boolean canStartChanneling() {
        return this.en.getHealth() < en.getMaxHealth() * 0.5F;
    }

    @Override
    public void onHitBy(DamageSource source, float amount) {

        EntityCap.UnitData unitdata = Load.Unit(en);

        ResourcesData.Context hp = new ResourcesData.Context(unitdata, en, ResourcesData.Type.HEALTH,
            en.getMaxHealth() * 0.02F,
            ResourcesData.Use.RESTORE
        );

        unitdata.getResources()
            .modify(hp);

        ParticleEnum.sendToClients(
            en.getBlockPos(), en.world,
            new ParticlePacketData(en.getPos(), ParticleEnum.AOE).radius(en.getHeight() * 0.5F)
                .motion(new Vec3d(0, 0, 0))
                .type(ParticleTypes.HEART)
                .amount((int) (en.getHeight() * 3)));
    }

    @Override
    public int getTicksNeeded() {
        return 80;
    }

    @Override
    public int Weight() {
        return 1500;
    }
}

