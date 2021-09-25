package com.robertx22.age_of_exile.gui.overlays.mob_bar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class MobBarScreen extends AbstractGui {

    private Minecraft mc;

    ResourceLocation TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/mob_bar/mob_bar.png");

    public MobBarScreen() {
        super();
        this.mc = Minecraft.getInstance();

    }

    int xSize = 102;
    int ySize = 7;

    LivingEntity en;
    int ticks = 0;

    public void onHudRender(MatrixStack matrix, float v) {

        try {

            if (true) {
                return;
            }
            if (!ClientConfigs.getConfig().RENDER_SIMPLE_MOB_BAR.get()) {
                return;
            }

            Entity e = LookUtils.getEntityLookedAt(mc.player);

            if (e instanceof LivingEntity) {
                en = (LivingEntity) e;
            } else {
                ticks++;
                if (ticks > 20) {
                    en = null;
                    ticks = 0;
                }
            }

            if (en != null) {

                EntityData data = Load.Unit(en);

                if (data != null && data.getUnit() != null) {
                    int currentHp = (int) en.getHealth();
                    int maxHP = (int) HealthUtils.getMaxHealth(en);
                    int percent = Math.round((float) currentHp / (float) maxHP * 100);

                    int height = mc.getWindow()
                        .getGuiScaledHeight();
                    int width = mc.getWindow()
                        .getGuiScaledWidth();

                    int x = width / 2 - xSize / 2;
                    int y = 30;

                    mc.getTextureManager()
                        .bind(TEX);

                    blit(matrix, x, y, 0, 0, xSize, ySize); // the bar

                    blit(matrix, x, y, 0, ySize, (int) ((float) xSize * percent / 100F), ySize); // inner fill texture

                    String name = CLOC.translate(data.getName());

                    mc.font.drawShadow(matrix, name, width / 2 - mc.font.width(name) / 2,
                        y - 10, TextFormatting.WHITE.getColor()
                    );

                    String hpText = (int) currentHp + "/" + (int) maxHP;

                    GuiUtils.renderScaledText(matrix,
                        width / 2, (int) (y + (float) ySize / 2) + 1, 0.75F, hpText, TextFormatting.GREEN);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
