package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vazkii.patchouli.client.base.ClientAdvancements;

import java.util.Objects;

public class AdvancementUtils {

    public static Advancement getAdvancement(World world, Identifier id) {
        if (world.isClient) {
            return AdvancementClientUtils.getAdvancement(world, id);
        } else {
            return MMORPG.server.getAdvancementLoader()
                .get(id);
        }
    }

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
            return ClientAdvancements.hasDone(id.toString()); // this is from patchouli, its a required library anyway
        }
    }

}
