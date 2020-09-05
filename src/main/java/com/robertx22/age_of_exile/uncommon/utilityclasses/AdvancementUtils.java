package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class AdvancementUtils {

    public static boolean didPlayerFinish(PlayerEntity player, Identifier id) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(id);

        if (player instanceof ServerPlayerEntity) {
            ServerAdvancementLoader loader = player.getServer()
                .getAdvancementLoader();

            ServerPlayerEntity sp = (ServerPlayerEntity) player;
            Advancement adv = loader.get(id);

            Objects.requireNonNull(adv);

            return sp.getAdvancementTracker()
                .getProgress(adv)
                .isDone();
        } else {
            return true; // todo
        }
    }

}
