package com.robertx22.age_of_exile.database.data.spells.entities.proj;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.age_of_exile.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.age_of_exile.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.EntitySpellData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class FireBombEntity extends EntityBaseProjectile {

    public FireBombEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public FireBombEntity(World worldIn) {
        super(ModRegistry.ENTITIES.FIRE_BOMB, worldIn);

    }

    @Override
    public double radius() {
        return 3.5F;
    }

    @Override
    protected void onImpact(HitResult result) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.COAL);
    }

    @Override
    public void onTick() {

        EntitySpellData sdata = getSpellData();
        int RADIUS = sdata.configs.get(SC.RADIUS)
            .intValue();

        if (this.inGround || this.age % 5 == 0) {
            if (!world.isClient) {
                LivingEntity caster = getCaster();

                if (caster == null) {
                    return;
                }

                List<LivingEntity> entities = EntityFinder.start(caster, LivingEntity.class, getPos())
                    .radius(RADIUS)
                    .build();

                if (entities.size() > 0) {

                    this.remove();

                    SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_EXPLODE, 1, 1);

                    entities.forEach(x -> {

                        DamageEffect dmg = dealSpellDamageTo(x, new ISpellEntity.Options().knockbacks(false)
                            .activatesEffect(false));

                        dmg.Activate();

                    });

                }
            }
        }

        if (world.isClient) {

            if (age % 2 == 0) {
                for (int i = 0; i < 20; i++) {
                    Vec3d p = GeometryUtils.getRandomHorizontalPosInRadiusCircle(
                        getPos().add(0, 0.2F, 0), RADIUS);
                    ParticleUtils.spawn(ParticleTypes.SMOKE, world, p);

                }
            }
        }
    }

}
