package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AdvancementClientUtils {
    public static Advancement getAdvancement(World world, ResourceLocation id) {
        ClientPacketListener conn = Minecraft.getInstance()
            .getConnection();
        ClientAdvancements cm = conn.getAdvancements();
        Advancement adv = cm.getAdvancements()
            .get(id);
        return adv;

    }

}
