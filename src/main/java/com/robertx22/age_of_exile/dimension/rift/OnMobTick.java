package com.robertx22.age_of_exile.dimension.rift;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class OnMobTick {

    static int TICKS_TO_FAIL = 20 * 60 * 2;

    public static void tick(LivingEntity en) {

        if (en.world.isClient) {
            return;
        }
        if (en instanceof PlayerEntity) {
            return;
        }

        if (WorldUtils.isRiftWorld((ServerWorld) en.world)) {

            if (en.age > TICKS_TO_FAIL / 2) {
                ParticleEnum.sendToClients(en, new ParticlePacketData(en.getPos()
                    .add(0, 0, 0), ParticleEnum.AOE).radius(0.3F)
                    .type(ParticleTypes.WITCH)
                    .amount(2)
                    .motion(new Vec3d(0, 0, 0)));

            }

            if (en.age > TICKS_TO_FAIL) { // TODO
                if (!Load.dungeonData(en.world).data.get(en.getBlockPos()).data.fail) {
                    en.kill();

                    Load.dungeonData(en.world).data.get(en.getBlockPos()).data.fail = true;

                    TeamUtils.forEachMember(en.world, en.getBlockPos(), x -> {

                        x.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 10));
                        x.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 10));

                        x.sendMessage(new LiteralText("Rift failed. The creatures were allowed to live for too long..")
                            .formatted(Formatting.RED), false);
                    });

                }

            }
        }
    }
}