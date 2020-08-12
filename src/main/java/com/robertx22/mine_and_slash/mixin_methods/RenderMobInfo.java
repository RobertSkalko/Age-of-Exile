package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.mob_affixes.base.MobAffix;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LookUtils;
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

import java.util.List;

public class RenderMobInfo {

    static Entity lastLooked;

    public static void renderLivingEntityLabelIfPresent(TextRenderer textRenderer, EntityRenderDispatcher dispatcher, LivingEntity entity,
                                                        MatrixStack matrixStack,
                                                        VertexConsumerProvider vertexConsumerProvider, int i) {
        try {
            double squaredDistance =
                dispatcher.getSquaredDistanceToCamera(entity);
            if (squaredDistance <= 300) {

                if (true) {
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

                float yOffset = entity.getHeight() + 0.5F;

                EntityCap.UnitData data = Load.Unit(entity);

                MutableText lvlcomp =
                    new LiteralText(" [" + data.getLevel() + "]").formatted(Formatting.YELLOW);

                Text text = data.getName(entity)
                    .append(lvlcomp);

                List<MobAffix> prefix = data.getUnit()
                    .getAffixes();

                float percent = entity.getHealth() / entity.getMaxHealth() * 100F;

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
                        hpText.append(new LiteralText((int) entity.getHealth() + "").formatted(Formatting.GOLD));
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

                textRenderer.draw(text,
                    -textRenderer.getWidth(text) / 2.0f,
                    -12, -1, true, matrix4f,
                    vertexConsumerProvider, false, bgColor, i);

                textRenderer.draw(hpText, -textRenderer.getWidth(hpText) / 2.0f,
                    0, -1, true, matrix4f,
                    vertexConsumerProvider, false, bgColor, i);

                if (!data.getUnit().affixes.isEmpty()) {

                    MutableText affixText = new LiteralText("");

                    boolean addedaffix = false;

                    for (MobAffix mobAffix : prefix) {
                        if (addedaffix) {
                            affixText.append(new LiteralText(", "));
                        }
                        affixText.append(mobAffix.locName()
                            .formatted(mobAffix.format));
                        addedaffix = true;
                    }

                    textRenderer.draw(affixText, -textRenderer.getWidth(affixText) / 2.0f,
                        -24, -1, true, matrix4f,
                        vertexConsumerProvider, false, bgColor, i);
                }

                matrixStack.pop();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
