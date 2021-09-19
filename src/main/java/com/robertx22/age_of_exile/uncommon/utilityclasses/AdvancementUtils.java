package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Objects;

public class AdvancementUtils {

    public static Advancement getAdvancement(World world, ResourceLocation id) {
        if (world.isClientSide) {
            return AdvancementClientUtils.getAdvancement(world, id);
        } else {
            return MMORPG.server.getAdvancements()
                .getAdvancement(id);
        }
    }

    public static boolean didPlayerFinish(PlayerEntity player, ResourceLocation id) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(id);

        if (player instanceof ServerPlayerEntity) {
            ServerAdvancementManager loader = player.getServer()
                .getAdvancements();

            ServerPlayerEntity sp = (ServerPlayerEntity) player;
            Advancement adv = loader.getAdvancement(id);

            Objects.requireNonNull(adv);

            return sp.getAdvancements()
                .getOrStartProgress(adv)
                .isDone();
        } else {
            return ClientAdvUtils.hasDone(id.toString()); // this is from patchouli, its a required library anyway
        }
    }

}
