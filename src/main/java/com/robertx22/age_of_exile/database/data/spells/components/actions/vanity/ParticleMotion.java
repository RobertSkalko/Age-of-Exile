package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.util.math.Vec3d;

public enum ParticleMotion {
    CasterLook {
        @Override
        public Vec3d getMotion(SpellCtx ctx) {
            Vec3d rot = ctx.caster.getRotationVector();
            return rot;
        }
    }, None {
        @Override
        public Vec3d getMotion(SpellCtx ctx) {
            return Vec3d.ZERO;
        }
    };

    public abstract Vec3d getMotion(SpellCtx ctx);
}