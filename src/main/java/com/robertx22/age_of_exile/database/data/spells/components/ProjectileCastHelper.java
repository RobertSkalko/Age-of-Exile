package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.EntitySavedSpellData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;

public class ProjectileCastHelper {

    LivingEntity caster;

    public float apart = 3;
    public float shootSpeed = 1;
    public int projectilesAmount = 1;
    public boolean gravity = true;
    EntityType projectile;
    EntitySavedSpellData data;

    public ProjectileCastHelper(LivingEntity caster, EntityType projectile, EntitySavedSpellData data) {
        this.projectile = projectile;
        this.caster = caster;
        this.data = data;

    }

    private void playSound(Entity en) {

    }

    public void cast() {
        World world = caster.world;

        float addYaw = 0;

        for (int i = 0; i < projectilesAmount; i++) {
            if (projectilesAmount > 1) {
                if (i < projectilesAmount / 2) {
                    addYaw -= apart / projectilesAmount;
                } else if (i == projectilesAmount / 2) {
                    addYaw = 0;
                } else if (i > projectilesAmount / 2) {
                    addYaw += apart / projectilesAmount;
                }
            }

            PersistentProjectileEntity en = (PersistentProjectileEntity) projectile.create(world);
            SpellUtils.setupProjectileForCasting(en, caster, shootSpeed, caster.pitch, caster.yaw + addYaw);
            SpellUtils.initSpellEntity(en, caster, data);
            caster.world.spawnEntity(en);
            playSound(en);
        }

    }

}

