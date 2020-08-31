package com.robertx22.age_of_exile.database.data.spells.blocks.holy_flower;

import com.robertx22.age_of_exile.database.data.spells.blocks.base.BaseSpellTileEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.EntitySpellData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class HolyFlowerTileEntity extends BaseSpellTileEntity {

    public HolyFlowerTileEntity() {
        super(ModRegistry.BLOCK_ENTITIES.HOLY_FLOWER);
    }

    @Override
    public void onTick() {

        if (world.isClient) {
            return;
        }

        EntitySpellData sdata = getSpellData();
        int TICK_RATE = sdata.configs.get(SC.TICK_RATE)
            .intValue();
        int RADIUS = sdata.configs.get(SC.RADIUS)
            .intValue();

        if (this.data.ticksExisted > durationInTicks() == false) {

            if (data.ticksExisted % TICK_RATE == 0) {

                LivingEntity caster = data.getCaster(world);

                ParticleEnum.sendToClients(
                    pos, world, new ParticlePacketData(pos, ParticleEnum.AOE).radius(RADIUS)
                        .motion(new Vec3d(0, 0, 0))
                        .type(ParticleTypes.DRIPPING_HONEY)
                        .amount((int) (30 * RADIUS)));

                List<LivingEntity> entities = EntityFinder.start(
                    caster, LivingEntity.class, new Vec3d(getPos().getX(), getPos().getY(), getPos().getZ()).add(0.5F, 0, 0.5F))
                    .radius(RADIUS)
                    .searchFor(EntityFinder.SearchFor.ALLIES)
                    .build();

                entities.forEach(x -> {

                    this.healTarget(x)
                        .Activate();

                    SoundUtils.playSound(x, SoundEvents.ITEM_CROP_PLANT, 1, 1);

                });

            }
        }

    }

}
