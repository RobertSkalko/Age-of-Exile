package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

public class RenderUtils {

    public static void render16Icon(Identifier tex, int x, int y) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(tex);
        DrawableHelper.blit(x, y, 0, 0, 16, 16, 16, 16);
    }

    public static void render32Icon(Identifier tex, int x, int y) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(tex);
        DrawableHelper.blit(x - 8, y - 8, 0, 0, 32, 32, 32, 32);
    }

    public static void renderIcons(List<ExactStatData> list, int x, int y) {

        y -= 8;

        for (ExactStatData stat : list) {

            MinecraftClient.getInstance()
                .getTextureManager()
                .bindTexture(stat.getStat()
                    .getIconLocation());

            DrawableHelper.blit(x, y, 0, 0, 16, 16, 16, 16);

            y += 16;

        }

    }

}