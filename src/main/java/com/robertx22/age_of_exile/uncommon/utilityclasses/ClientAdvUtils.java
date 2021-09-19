package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mixins.AccessorClientAdvancementManager;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class ClientAdvUtils {

    public static boolean hasDone(String advancement) {
        ResourceLocation id = ResourceLocation.tryParse(advancement);
        if (id != null) {
            ClientPacketListener conn = Minecraft.getInstance()
                .getConnection();
            if (conn != null) {
                ClientAdvancements cm = conn.getAdvancements();
                Advancement adv = cm.getAdvancements()
                    .get(id);
                if (adv != null) {
                    Map<Advancement, AdvancementProgress> progressMap = ((AccessorClientAdvancementManager) cm).getAdvancementProgresses();
                    AdvancementProgress progress = progressMap.get(adv);
                    return progress != null && progress.isDone();
                }
            }
        }
        return false;
    }

}
