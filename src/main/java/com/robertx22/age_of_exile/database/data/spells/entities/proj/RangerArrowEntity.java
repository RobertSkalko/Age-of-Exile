package com.robertx22.age_of_exile.database.data.spells.entities.proj;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.ImbueEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RangerArrowEntity extends EntityBaseProjectile {

    public RangerArrowEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public RangerArrowEntity(World worldIn) {
        super(ModRegistry.ENTITIES.RANGER_ARROW, worldIn);
    }

    @Override
    public void initSpellEntity() {
        try {
            this.imbued = this.getSpellData()
                .getCaster(world)
                .hasStatusEffect(ImbueEffect.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean imbued = false;

    @Override
    public double radius() {
        return 1;
    }

    @Override
    public void onTick() {

        if (imbued && world.isClient) {
            if (this.age > 1) {
                for (int i = 0; i < 1; i++) {
                    Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPos(), 0.15F);
                    ParticleUtils.spawn(ParticleTypes.WITCH, world, p);
                }
            }
        }
    }

    public void onHit(LivingEntity entity) {

        try {

            SpellDamageEffect dmg = this.getSetupSpellDamage(entity);

            if (imbued) {
                dmg.number *= 1.5F;
            }

            dmg.Activate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onImpact(HitResult result) {
        try {
            LivingEntity entityHit = getEntityHit(result, 0.3D);

            if (entityHit != null) {
                if (world.isClient) {
                    SoundUtils.playSound(this, SoundEvents.ENTITY_GENERIC_HURT, 1F, 0.9F);
                }

                if (!entityHit.world.isClient) {
                    onHit(entityHit);
                }

            } else {
                if (world.isClient) {
                    SoundUtils.playSound(this, SoundEvents.BLOCK_STONE_HIT, 0.7F, 0.9F);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.remove();
    }

    @Override
    public void writeCustomDataToTag(CompoundTag nbt) {
        super.writeCustomDataToTag(nbt);
        nbt.putBoolean("imbued", imbued);

    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbt) {
        super.readCustomDataFromTag(nbt);
        this.imbued = nbt.getBoolean("imbued");
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.AIR);
    }

}
