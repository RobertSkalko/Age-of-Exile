package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;

public class RenderMobInfo {

    static Entity lastLooked;

    public static void renderLivingEntityLabelIfPresent(TextRenderer textRenderer, EntityRenderDispatcher dispatcher, LivingEntity entity,
                                                        MatrixStack matrixStack,
                                                        VertexConsumerProvider vertex, int i) {
        try {

            if (!ModConfig.get().client.RENDER_MOB_HEALTH_GUI) {
                return;
            }

            double squaredDistance =
                dispatcher.getSquaredDistanceToCamera(entity);
            if (squaredDistance <= 300) {

                if (ModConfig.get().client.ONLY_RENDER_MOB_LOOKED_AT) {
                    if (LookUtils.getEntityLookedAt(MinecraftClient.getInstance().player) != entity) {
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
                if (entity.hasPassenger(MinecraftClient.getInstance().player)) {
                    return; // dont display horse's bar if the player is riding it
                }

                float yOffset = entity.getHeight() + 0.5F;

                EntityCap.UnitData data = Load.Unit(entity);

                boolean hidelvl = data.getLevel() - 10 > Load.Unit(MinecraftClient.getInstance().player)
                    .getLevel();

                MutableText lvlcomp =
                    new LiteralText(" [" + data.getLevel() + "]").formatted(Formatting.YELLOW);

                if (hidelvl) {
                    lvlcomp =
                        new LiteralText(" [" + "???" + "]").formatted(Formatting.YELLOW);
                }

                Text text = data.getName()
                    .append(lvlcomp);

                float percent = HealthUtils.getCurrentHealth(entity) / HealthUtils.getMaxHealth(entity) * 100F;

                MutableText hpText = new LiteralText("[").formatted(Formatting.DARK_RED);
                int times = 0;

                for (int x = 0; x < 10; x++) {
                    times++;

                    if (percent > 0) {
                        hpText.append(new LiteralText("|")
                            .formatted(Formatting.RED)
                        );
                    } else {
                        hpText.append(new LiteralText("|")
                            .formatted(Formatting.DARK_RED)
                        );
                    }

                    if (times == 5) {
                        hpText.append(new LiteralText((int) HealthUtils.getCurrentHealth(entity) + "").formatted(Formatting.GOLD));
                    }
                    percent -= 10;

                }

                hpText.append(new LiteralText("]").formatted(Formatting.DARK_RED));

                matrixStack.push();
                matrixStack.translate(0.0D, yOffset, 0.0D);
                matrixStack.multiply(dispatcher.getRotation());
                matrixStack.scale(-0.025F, -0.025F, 0.025F);

                Matrix4f matrix4f = matrixStack.peek()
                    .getModel();
                float bgAlpha =
                    MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
                int bgColor = (int) (bgAlpha * 255.0F) << 24;

                if (matrix4f == null || textRenderer == null) {
                    return;
                }

                try {
                    textRenderer.draw(text,
                        -textRenderer.getWidth(text) / 2.0f,
                        -12, -1, true, matrix4f,
                        vertex, false, bgColor, i);

                    textRenderer.draw(hpText, -textRenderer.getWidth(hpText) / 2.0f,
                        0, -1, true, matrix4f,
                        vertex, false, bgColor, i);

                    MobRarity rar = Database.MobRarities()
                        .get(data.getRarity());

                    String icon = rar.name_add;
                    if (!icon.isEmpty()) {
                        icon = rar.textFormatting() + icon;

                        matrixStack.scale(2, 2, 2);
                        textRenderer.draw(icon, -textRenderer.getWidth(icon) / 2.0f,
                            -15, -1, true, matrix4f,
                            vertex, false, Formatting.YELLOW
                                .getColorIndex(), i);
                        matrixStack.scale(0.5F, 0.5F, 0.5F);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                matrixStack.pop();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
