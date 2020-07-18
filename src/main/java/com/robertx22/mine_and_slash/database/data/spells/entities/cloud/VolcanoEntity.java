package com.robertx22.mine_and_slash.database.data.spells.entities.cloud;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseInvisibleEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.mmorpg.registers.common.EntityRegister;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.RGB;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.List;

public class VolcanoEntity extends BaseInvisibleEntity {

    public VolcanoEntity(World world) {
        super(EntityRegister.VOLCANO, world);
    }

    public VolcanoEntity(EntityType type, World world) {
        super(type, world);
    }

    public VolcanoEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(EntityRegister.VOLCANO, world);
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
                            x, y + +yRandom, z, radius);

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

}
