package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.PUSH_STRENGTH;

public class SpellMotionAction extends SpellAction {

    public SpellMotionAction() {
        super(Arrays.asList(PUSH_STRENGTH, MapField.MOTION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        try {
            float str = data.get(PUSH_STRENGTH)
                .floatValue();

            Vec3d motion = ParticleMotion.valueOf(data.get(MapField.MOTION))
                .getMotion(ctx.vecPos, ctx)
                .multiply(str);

            SetAdd setAdd = data.getSetAdd();

            targets.forEach(x -> {
                if (setAdd == SetAdd.SET) {
                    x.setVelocity(motion);
                } else {
                    x.addVelocity(motion.x, motion.y, motion.z);
                }
            });

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public MapHolder create(SetAdd setadd, Double str, ParticleMotion motion) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(PUSH_STRENGTH, str);
        d.put(MapField.MOTION, motion.name());
        d.put(MapField.SET_ADD, setadd.name());
        return d;
    }

    @Override
    public String GUID() {
        return "motion";
    }
}
