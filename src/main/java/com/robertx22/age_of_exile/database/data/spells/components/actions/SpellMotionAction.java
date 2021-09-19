package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.util.math.vector.Vector3d;

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
            if (!ctx.world.isClientSide) {
                float str = data.get(PUSH_STRENGTH)
                    .floatValue();

                ParticleMotion pm = ParticleMotion.valueOf(data.get(MapField.MOTION));

                Vector3d motion = pm
                    .getMotion(ctx.vecPos, ctx)
                    .scale(str);

                SetAdd setAdd = data.getSetAdd();

                if (data.getOrDefault(MapField.IGNORE_Y, false)) {
                    if (setAdd == SetAdd.ADD) {
                        motion = new Vector3d(motion.x, 0, motion.z);
                    }
                }

                for (LivingEntity x : targets) {
                    if (setAdd == SetAdd.SET) {
                        if (data.getOrDefault(MapField.IGNORE_Y, false)) {
                            x.setDeltaMovement(new Vector3d(motion.x, x.getDeltaMovement().y, motion.z));
                        } else {
                            x.setDeltaMovement(new Vector3d(motion.x, motion.y, motion.z));
                        }
                    } else {
                        x.push(motion.x, motion.y, motion.z);
                    }

                    PlayerStream.watching(x.level, x.blockPosition())
                        .forEach((p) -> {

                            ((ServerPlayerEntity) p).connection.send(new ClientboundSetEntityMotionPacket(x));
                            x.hurtMarked = false;
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
