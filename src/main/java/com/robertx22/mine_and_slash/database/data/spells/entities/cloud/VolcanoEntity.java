package com.robertx22.mine_and_slash.database.data.spells.entities.cloud;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseInvisibleEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.RGB;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class VolcanoEntity extends BaseInvisibleEntity {

    public VolcanoEntity(World world) {
        super(ModRegistry.ENTITIES.VOLCANO, world);
    }

    public VolcanoEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public void initSpellEntity() {

    }

    public void summonParticle(Vec3d p) {
    }

    @Override
    public void onTick() {
        try {

            float radius = getSpellData().configs.get(SC.RADIUS);
            int tickrate = getSpellData().configs.get(SC.TICK_RATE)
                .intValue();

            if (this.age % tickrate == 0) {

                SoundUtils.playSound(this, SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);

                if (!this.world.isClient) {

                    List<LivingEntity> entities = EntityFinder.start(
                        getCaster(), LivingEntity.class, getPosVector())
                        .radius(radius)
                        .build();

                    for (LivingEntity target : entities) {

                        this.dealSpellDamageTo(target, new ISpellEntity.Options().knockbacks(true));

                    }

                }

                if (world.isClient) {

                    for (int i = 1; i < 12; i++) {
                        double speed = (random.nextBoolean() ? 1 : -1) * 0.1 + 0.05 * random.nextDouble();

                        float yRandom = (float) RandomUtils.RandomRange(1, 100) / 80F;

                        Vec3d p = GeometryUtils.getRandomHorizontalPosInRadiusCircle(
                            getX(), getY() + +yRandom, getZ(), radius);

                        for (int n = 0; n < 3; n++) {
                            ParticleUtils.spawn(ParticleTypes.LAVA, world, p.x, p.y, p.z, 0, 0.5f, 0);

                        }

                        ParticleUtils.spawn(ParticleTypes.FALLING_LAVA, world, p.x, p.y, p.z, 0, 1, 0);

                        RGB color = Elements.Fire.getRGBColor();
                        ParticleUtils.spawn(new DustParticleEffect(color.getR(), color.getG(), color.getB(), 1F),
                            world, p.x, p.y, p.z, 0, 0, 0
                        );

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
