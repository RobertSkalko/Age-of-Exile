package com.robertx22.age_of_exile.gui.overlays.death_stats;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.text.TextFormatting;

import java.util.Map;

public class DeathStatsOverlay extends AbstractGui implements HudRenderCallback {

    public DeathStatsOverlay() {
        super();
    }

    Minecraft mc = Minecraft.getInstance();

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {
            if (mc.player == null) {
                return;
            }
            if (mc.options.renderDebug || mc.player.isCreative() || mc.player.isSpectator()) {
                return;
            }
            if (!ModConfig.get().client.RENDER_DEATH_STATISTICS) {
                return;
            }

            if (mc.player.isDeadOrDying()) {
                RPGPlayerData data = Load.playerRPGData(mc.player);

                DeathStatsData stats = data.deathStats;

                int x = mc.getWindow()
                    .getGuiScaledWidth() / 2;
                int y = mc.getWindow()
                    .getGuiScaledHeight() / 2 + 50;

                TextUtils.renderText(matrix, 1, "Damage Taken:", x, y, TextFormatting.WHITE);

                int totaldmg = stats.dmg.values()
                    .stream()
                    .mapToInt(i -> i.intValue())
                    .sum();

                for (Map.Entry<Elements, Float> entry : stats.dmg.entrySet()) {

                    int percent = (int) (entry.getValue() / totaldmg * 100);
                    String text = entry.getKey().dmgName + ": " + entry.getValue()
                        .intValue() + " (" + percent + "%)";
                    y += 12;
                    TextUtils.renderText(matrix, 1, text, x, y, entry.getKey().format);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}