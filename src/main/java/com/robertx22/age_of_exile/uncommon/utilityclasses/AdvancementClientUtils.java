package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AdvancementClientUtils {
    public static Advancement getAdvancement(World world, Identifier id) {
        ClientPlayNetworkHandler conn = MinecraftClient.getInstance()
            .getNetworkHandler();
        ClientAdvancementManager cm = conn.getAdvancementHandler();
        Advancement adv = cm.getManager()
            .get(id);
        return adv;

    }

}
