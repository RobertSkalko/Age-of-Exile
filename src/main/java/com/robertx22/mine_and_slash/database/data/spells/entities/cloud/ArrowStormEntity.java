package com.robertx22.mine_and_slash.database.data.spells.entities.cloud;

import com.robertx22.mine_and_slash.database.data.spells.SpellUtils;
import com.robertx22.mine_and_slash.database.data.spells.entities.bases.BaseCloudEntity;
import com.robertx22.mine_and_slash.database.data.spells.entities.proj.RangerArrowEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ArrowStormEntity extends BaseCloudEntity {

    public ArrowStormEntity(World world) {
        super(ModRegistry.ENTITIES.ARROW_STORM, world);
    }

    public ArrowStormEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public void initSpellEntity() {

    }

    @Override
    public void onHit(LivingEntity entity) {

    }

    @Override
    public void summonFallParticle(Vec3d p) {

    }

    public static int ARROWS_PER_SUMMON = 12;

    @Override
    public void onTick() {

        int tickrate = getSpellData().configs.get(SC.TICK_RATE)
            .intValue();

        super.onTick();

        if (this.age % tickrate == 0) {
            LivingEntity caster = getCaster();

            if (!this.world.isClient) {
                for (int i = 0; i < ARROWS_PER_SUMMON; i++) {
                    float yRandom = (float) RandomUtils.RandomRange(1, 100) / 40F;
                    float height = 4;
                    Vec3d p = GeometryUtils.getRandomHorizontalPosInRadiusCircle(
                        getX(), getY() + height + yRandom, getZ(), radius());

                    RangerArrowEntity en = SpellUtils.getSpellEntity(getSpellData().configs, new RangerArrowEntity(world), getSpellData().skillgem, caster);
                    SpellUtils.setupProjectileForCasting(en, caster, 1.5F);
                    en.setVelocity(new Vec3d(0, -1, 0));
                    en.refreshPositionAndAngles(p.x, p.y, p.z, 0, 0);
                    caster.world.spawnEntity(en);

                    SoundUtils.playSound(en, SoundEvents.ENTITY_ARROW_SHOOT, 1, 1);
                }
            }
        }

    }

    public float radius() {
        return 2.5F;
    }

}