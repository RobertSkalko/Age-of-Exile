package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ClientTextureUtils {

    public static boolean textureExists(Identifier id) {
        return MinecraftClient.getInstance()
            .getResourceManager()
            .containsResource(id);
    }

}