package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class ProjectileCastHelper {

    LivingEntity caster;

    public boolean silent = false;
    public float apart = 3;
    public float shootSpeed = 1;
    public int projectilesAmount = 1;
    public boolean gravity = true;
    EntityType projectile;
    EntitySavedSpellData data;
    MapHolder holder;
    Vector3d pos;

    public CastType castType = CastType.SPREAD_OUT_IN_RADIUS;

    public enum CastType {
        SPREAD_OUT_IN_RADIUS, SPREAD_OUT_HORIZONTAL
    }

    public float pitch;

    public float yaw;

    public boolean fallDown = false;

    public ProjectileCastHelper(Vector3d pos, MapHolder holder, LivingEntity caster, EntityType projectile, EntitySavedSpellData data) {
        this.projectile = projectile;
        this.caster = caster;
        this.data = data;
        this.holder = holder;
        this.pos = pos;

        this.pitch = caster.xRot;
        this.yaw = caster.yRot;
    }

    public void cast() {
        World world = caster.level;

        float addYaw = 0;

        Vector3d posAdd = new Vector3d(0, 0, 0);

        for (int i = 0; i < projectilesAmount; i++) {

            if (this.castType == CastType.SPREAD_OUT_IN_RADIUS) {
                if (projectilesAmount > 1) {
                    if (i < projectilesAmount / 2) {
                        addYaw -= apart / projectilesAmount;
                    } else if (i == projectilesAmount / 2) {
                        addYaw = 0;
                    } else if (i > projectilesAmount / 2) {
                        addYaw += apart / projectilesAmount;
                    }
                }
            }
            if (this.castType == CastType.SPREAD_OUT_HORIZONTAL) { // TODO
                if (projectilesAmount > 1) {
                    if (i < projectilesAmount / 2) {

                    } else if (i == projectilesAmount / 2) {
                        posAdd = Vector3d.ZERO;
                    } else if (i > projectilesAmount / 2) {

                    }
                }
            }

            AbstractArrow en = (AbstractArrow) projectile.create(world);
            SpellUtils.shootProjectile(pos.add(posAdd), en, caster, shootSpeed, pitch, yaw + addYaw);
            SpellUtils.initSpellEntity(en, caster, data, holder);

            if (fallDown) {
                en.setDeltaMovement(0, -1, 0);
            }
            en.setSilent(silent);

            caster.level.addFreshEntity(en);
        }

    }

}

