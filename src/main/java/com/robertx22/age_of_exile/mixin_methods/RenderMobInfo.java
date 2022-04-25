package com.robertx22.age_of_exile.mixin_methods;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RenderMobInfo {

    static Entity lastLooked;

    public static void renderLivingEntityLabelIfPresent(FontRenderer textRenderer, EntityRendererManager dispatcher, LivingEntity entity,
                                                        MatrixStack matrixStack,
                                                        IRenderTypeBuffer vertex, int i) {
        try {

            if (!ClientConfigs.getConfig().RENDER_MOB_HEALTH_GUI.get()) {
                return;
            }

            double squaredDistance =
                dispatcher.distanceToSqr(entity);
            if (squaredDistance <= 300) {

                if (ClientConfigs.getConfig().ONLY_RENDER_MOB_LOOKED_AT.get()) {
                    if (LookUtils.getEntityLookedAt(Minecraft.getInstance().player) != entity) {
                        if (lastLooked != entity) {
                            return;
                        }
                    } else {
                        lastLooked = entity;
                    }
                }
                if (entity instanceof ArmorStandEntity) {
                    return;
                }
                if (entity.hasPassenger(Minecraft.getInstance().player)) {
                    return; // dont display horse's bar if the player is riding it
                }

                float yOffset = entity.getBbHeight() + 0.5F;

                EntityData data = Load.Unit(entity);

                int currentHP = HealthUtils.getCurrentHealth(entity);

                if (currentHP == 0) {
                    return;
                }

                IFormattableTextComponent hpText = new StringTextComponent(currentHP + "")
                    .withStyle(TextFormatting.GREEN)
                    .append(new StringTextComponent("\u2764").withStyle(TextFormatting.RED)); // todo

                IFormattableTextComponent lvlcomp =

                    new StringTextComponent("Lvl " + data.getLevel() + "").withStyle(TextFormatting.YELLOW);

                ITextComponent text = lvlcomp.append(new StringTextComponent(" ").append(entity.getName())
                        .withStyle(TextFormatting.RED))
                    .append(" ")
                    .append(hpText);

                matrixStack.pushPose();
                matrixStack.translate(0.0D, yOffset, 0.0D);
                matrixStack.mulPose(dispatcher.cameraOrientation());
                matrixStack.scale(-0.025F, -0.025F, 0.025F);

                Matrix4f matrix4f = matrixStack.last()
                    .pose();
                float bgAlpha =
                    Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                int bgColor = (int) (bgAlpha * 255.0F) << 24;

                if (matrix4f == null || textRenderer == null) {
                    return;
                }

                try {
                    textRenderer.drawInBatch(text,
                        -textRenderer.width(text) / 2.0f,
                        -12, -1, true, matrix4f,
                        vertex, false, bgColor, i);

                    MobRarity rar = ExileDB.MobRarities()
                        .get(data.getRarity());

                    IFormattableTextComponent rarname = rar.locName()
                        .withStyle(rar.textFormatting());

                    textRenderer.drawInBatch(rarname, -textRenderer.width(rarname) / 2.0f,
                        0, -1, true, matrix4f,
                        vertex, false, bgColor, i);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                matrixStack.popPose();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
