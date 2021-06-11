package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
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
            if (!ctx.world.isClient) {
                float str = data.get(PUSH_STRENGTH)
                    .floatValue();

                ParticleMotion pm = ParticleMotion.valueOf(data.get(MapField.MOTION));

                Vec3d motion = pm
                    .getMotion(ctx.vecPos, ctx)
                    .multiply(str);

                SetAdd setAdd = data.getSetAdd();

                if (data.getOrDefault(MapField.IGNORE_Y, false)) {
                    if (setAdd == SetAdd.ADD) {
                        motion = new Vec3d(motion.x, 0, motion.z);
                    }
                }

                for (LivingEntity x : targets) {
                    if (setAdd == SetAdd.SET) {
                        if (data.getOrDefault(MapField.IGNORE_Y, false)) {
                            x.setVelocity(new Vec3d(motion.x, x.getVelocity().y, motion.z));
                        } else {
                            x.setVelocity(new Vec3d(motion.x, motion.y, motion.z));
                        }
                    } else {
                        x.addVelocity(motion.x, motion.y, motion.z);
                    }

                    PlayerStream.watching(x.world, x.getBlockPos())
                        .forEach((p) -> {

                            ((ServerPlayerEntity) p).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(x));
                            x.velocityModified = false;
                        });

                }
            }

        } catch (Exception e) {
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
