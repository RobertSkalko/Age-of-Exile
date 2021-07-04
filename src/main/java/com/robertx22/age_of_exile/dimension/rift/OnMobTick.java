package com.robertx22.age_of_exile.dimension.rift;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class OnMobTick {

    static int TICKS_TO_FAIL = 20 * 60 * 5;

    public static void tick(LivingEntity en) {

        if (en.world.isClient) {
            return;
        }

        if (WorldUtils.isRiftWorld((ServerWorld) en.world)) {
            if (en.age > TICKS_TO_FAIL) { // TODO

                Load.dungeonData(en.world).data.get(en.getBlockPos()).data.fail = true;

                en.world.getPlayers(TargetPredicate.DEFAULT, en, en.getBoundingBox()
                        .expand(100))
                    .forEach(x -> {
                        x.sendMessage(new LiteralText("Rift failed. The creatures were allowed to live for too long..")
                            .formatted(Formatting.RED), false);
                    });

            }

        }
    }
}