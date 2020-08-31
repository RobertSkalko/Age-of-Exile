package com.robertx22.age_of_exile.database.data.spells.entities.proj;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.library_of_exile.utils.GeometryUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class LightningTotemEntity extends EntityBaseProjectile {

    public LightningTotemEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public LightningTotemEntity(World worldIn) {
        super(ModRegistry.ENTITIES.LIGHTNING_TOTEM, worldIn);

    }

    @Override
    public double radius() {
        return 2F;
    }

    @Override
    protected void onImpact(HitResult result) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Override
    public void onTick() {

        int tickRate = getSpellData().configs.get(SC.TICK_RATE)
            .intValue();

        if (this.age % tickRate == 0) {
            if (!world.isClient) {

                List<LivingEntity> entities = EntityFinder.start(getCaster(), LivingEntity.class, getPos())
                    .radius(radius())
                    .build();

                entities.forEach(x -> {

                    SpellDamageEffect dmg = dealSpellDamageTo(x, new Options().activatesEffect(false)
                        .knockbacks(false));

                    dmg.Activate();

                    SoundUtils.playSound(this, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1, 1);

                });
            } else {

                SoundUtils.playSound(this, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1, 1);

            }
        }

        if (this.inGround && world.isClient) {

            for (int i = 0; i < 25; i++) {
                Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPos(), (float) radius());
                ParticleUtils.spawn(ModRegistry.PARTICLES.THUNDER, world, p);

            }

        }

    }

}
