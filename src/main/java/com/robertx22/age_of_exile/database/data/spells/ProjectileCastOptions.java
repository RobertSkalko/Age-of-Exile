package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class ProjectileCastOptions {

    LivingEntity caster;

    public float apart = 3;
    public float shootSpeed = 1;
    public int projectilesAmount = 1;
    SpellCastContext ctx;
    EntityType projectile;

    public ProjectileCastOptions(SpellCastContext ctx, EntityType projectile) {
        this.projectile = projectile;
        this.caster = ctx.caster;
        this.ctx = ctx;

    }

    private void playSound(Entity en) {
        if (ctx.spell.getImmutableConfigs()
            .sound() != null) {
            ctx.caster.world.playSoundFromEntity(null, en, ctx.spell.getImmutableConfigs()
                .sound(), SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
    }

    public void cast() {
        World world = caster.world;

        if (projectilesAmount > 1) {

            float addYaw = 0;

            for (int i = 0; i < projectilesAmount; i++) {

                if (i < projectilesAmount / 2) {
                    addYaw -= apart / projectilesAmount;
                } else if (i == projectilesAmount / 2) {
                    addYaw = 0;
                } else if (i > projectilesAmount / 2) {
                    addYaw += apart / projectilesAmount;
                }
                PersistentProjectileEntity en = (PersistentProjectileEntity) SpellUtils.getSpellEntity(ctx.configForSummonedEntities, projectile.create(world), ctx.calcData, caster);
                SpellUtils.setupProjectileForCasting(en, caster, shootSpeed, caster.pitch,
                    caster.yaw + addYaw
                );
                caster.world.spawnEntity(en);

                playSound(en);
            }
        } else {
            PersistentProjectileEntity en = (PersistentProjectileEntity) SpellUtils.getSpellEntity(ctx.configForSummonedEntities, projectile.create(world), ctx.calcData, caster);
            SpellUtils.setupProjectileForCasting(en, caster, shootSpeed, caster.pitch, caster.yaw
            );
            caster.world.spawnEntity(en);

            playSound(en);

        }

    }

}
