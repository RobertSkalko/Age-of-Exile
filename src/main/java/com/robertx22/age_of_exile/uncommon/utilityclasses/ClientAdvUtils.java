package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mixins.AccessorClientAdvancementManager;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ClientAdvUtils {

    public static boolean hasDone(String advancement) {
        Identifier id = Identifier.tryParse(advancement);
        if (id != null) {
            ClientPlayNetworkHandler conn = MinecraftClient.getInstance()
                .getNetworkHandler();
            if (conn != null) {
                ClientAdvancementManager cm = conn.getAdvancementHandler();
                Advancement adv = cm.getManager()
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
