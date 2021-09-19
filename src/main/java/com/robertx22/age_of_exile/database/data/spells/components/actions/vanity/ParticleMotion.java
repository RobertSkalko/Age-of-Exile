package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.util.math.vector.Vector3d;

public enum ParticleMotion {
    CasterLook {
        @Override
        public Vector3d getMotion(Vector3d particlePos, SpellCtx ctx) {
            Vector3d rot = ctx.caster.getLookAngle();
            return rot;
        }
    },
    Upwards {
        @Override
        public Vector3d getMotion(Vector3d particlePos, SpellCtx ctx) {
            return new Vector3d(0, 1F, 0);
        }
    },
    Downwards {
        @Override
        public Vector3d getMotion(Vector3d particlePos, SpellCtx ctx) {
            return new Vector3d(0, -1F, 0);
        }
    },
    OutwardMotion {
        @Override
        public Vector3d getMotion(Vector3d particlePos, SpellCtx ctx) {

            Vector3d c = ctx.vecPos;
            c = new Vector3d(c.x, 0, c.z);

            Vector3d p = particlePos;
            p = new Vector3d(p.x, 0, p.z);

            return p.subtract(c);
        }
    },
    None {
        @Override
        public Vector3d getMotion(Vector3d particlePos, SpellCtx ctx) {
            return Vector3d.ZERO;
        }
    };

    public abstract Vector3d getMotion(Vector3d particlePos, SpellCtx ctx);
}