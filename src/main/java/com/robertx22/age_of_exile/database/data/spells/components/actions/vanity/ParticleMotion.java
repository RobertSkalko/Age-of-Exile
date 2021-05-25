package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.util.math.Vec3d;

public enum ParticleMotion {
    CasterLook {
        @Override
        public Vec3d getMotion(Vec3d particlePos, SpellCtx ctx) {
            Vec3d rot = ctx.caster.getRotationVector();
            return rot;
        }
    },
    Upwards {
        @Override
        public Vec3d getMotion(Vec3d particlePos, SpellCtx ctx) {
            return new Vec3d(0, 1F, 0);
        }
    },
    Downwards {
        @Override
        public Vec3d getMotion(Vec3d particlePos, SpellCtx ctx) {
            return new Vec3d(0, -1F, 0);
        }
    },
    OutwardMotion {
        @Override
        public Vec3d getMotion(Vec3d particlePos, SpellCtx ctx) {

            Vec3d c = ctx.vecPos;
            c = new Vec3d(c.x, 0, c.z);

            Vec3d p = particlePos;
            p = new Vec3d(p.x, 0, p.z);

            return p.subtract(c);
        }
    },
    None {
        @Override
        public Vec3d getMotion(Vec3d particlePos, SpellCtx ctx) {
            return Vec3d.ZERO;
        }
    };

    public abstract Vec3d getMotion(Vec3d particlePos, SpellCtx ctx);
}