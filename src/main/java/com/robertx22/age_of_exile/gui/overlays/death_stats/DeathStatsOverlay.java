package com.robertx22.age_of_exile.gui.overlays.death_stats;

import com.robertx22.age_of_exile.capability.player.PlayerDeathData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.PlayerDeathStatistics;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

import java.util.Map;

public class DeathStatsOverlay extends DrawableHelper implements HudRenderCallback {

    public DeathStatsOverlay() {
        super();
    }

    MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {
            if (mc.player == null) {
                return;
            }
            if (mc.options.debugEnabled || mc.player.isCreative() || mc.player.isSpectator()) {
                return;
            }
            if (!ModConfig.get().client.RENDER_DEATH_STATISTICS) {
                return;
            }

            if (mc.player.isDead()) {
                PlayerDeathData data = ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(mc.player);

                PlayerDeathStatistics stats = data.deathStats;

                int x = mc.getWindow()
                    .getScaledWidth() / 2;
                int y = mc.getWindow()
                    .getScaledHeight() / 2 + 50;

                TextUtils.renderText(matrix, 1, "Damage Taken:", x, y, Formatting.WHITE);

                for (Map.Entry<Elements, Float> entry : stats.dmg.entrySet()) {
                    String text = entry.getKey().dmgName + ": " + entry.getValue()
                        .intValue();
                    y += 12;
                    TextUtils.renderText(matrix, 1, text, x, y, entry.getKey().format);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}