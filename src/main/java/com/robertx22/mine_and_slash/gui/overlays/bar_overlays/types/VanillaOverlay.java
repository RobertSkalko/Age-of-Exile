package com.robertx22.mine_and_slash.gui.overlays.bar_overlays.types;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayerGUIs;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class VanillaOverlay extends DrawableHelper implements HudRenderCallback {

    public VanillaOverlay() {
        super();
    }

    MinecraftClient mc = MinecraftClient.getInstance();

    int ticks = 0;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        if (mc.player == null) {
            return;
        }
        try {

            if (!ModConfig.get().client.PLAYER_GUI_TYPE.equals(PlayerGUIs.Vanilla)) {
                return;
            }

            PlayerEntity en = mc.player;
            UnitData data = Load.Unit(en);

            if (en.isCreative() || en.isSpectator()) {
                return;
            }

            if (en == null || data == null) {
                return;
            }

            ticks++;

            Identifier TEX = new Identifier("mmorpg", "textures/gui/overlay/vanilla_overlay.png");

            mc.getTextureManager()
                .bindTexture(TEX);

            int SPACING_Y = 10;

            int width = mc.getWindow()
                .getScaledWidth();
            int height = mc.getWindow()
                .getScaledHeight();

            int x = width / 2 - 91;
            int y = height - 39 - SPACING_Y;

            if (en.getArmor() > 0) {
                y -= SPACING_Y;
            }

            int leftY = ModConfig.get().client.LEFT_VANILLA_LIKE_BARS_Y__POS_ADJUST;
            int rightY = ModConfig.get().client.RIGHT_VANILLA_LIKE_BARS_Y__POS_ADJUST;

            renderElement(matrix, ticks, Type.MAGIC_SHIELD, x, y + leftY, mc, en, data);

            x = width / 2 + 81;
            y = height - 39 - SPACING_Y;

            int air = en.getAir();
            if (en.isSubmergedIn(FluidTags.WATER) || air < 300) {
                y -= SPACING_Y;
            }

            renderElement(matrix, ticks, Type.MANA, x, y + rightY, mc, en, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum Type {

        MANA {
            @Override
            public Side getSide() {
                return Side.RIGHT;
            }

            @Override
            public float getCurrent(LivingEntity en, UnitData data) {
                return data.getResources()
                    .getMana();

            }

            @Override
            public float getMax(LivingEntity en, UnitData data) {
                return data.getUnit()
                    .manaData()
                    .getAverageValue();

            }

            @Override
            public int yPosTexture(UnitData data) {

                return 10;
            }

        },
        MAGIC_SHIELD {
            @Override
            public Side getSide() {
                return Side.LEFT;
            }

            @Override
            public float getCurrent(LivingEntity en, UnitData data) {
                return data.getResources()
                    .getMagicShield();
            }

            @Override
            public float getMax(LivingEntity en, UnitData data) {
                return data.getUnit()
                    .magicShieldData()
                    .getAverageValue();
            }

            @Override
            public int yPosTexture(UnitData data) {
                return 0;
            }
        };

        public abstract Side getSide();

        public abstract float getCurrent(LivingEntity en, UnitData data);

        public abstract float getMax(LivingEntity en, UnitData data);

        public abstract int yPosTexture(UnitData data);

    }

    enum Side {
        LEFT, RIGHT;
    }

    HashMap<Type, Integer> lastValues = new HashMap<>();
    HashMap<Type, Integer> changedTicksLeft = new HashMap<>();

    public void renderElement(MatrixStack matrix, int ticks, Type type, int x, int y, MinecraftClient mc, LivingEntity en, UnitData data) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.enableBlend();

        int current = (int) type.getCurrent(en, data);
        int max = (int) type.getMax(en, data);

        boolean changed = lastValues.getOrDefault(type, 0) != current;

        int changedTicksRem = changedTicksLeft.getOrDefault(type, 0);
        if (changed) {
            changedTicksLeft.put(type, 10);
        } else {
            changedTicksLeft.put(type, changedTicksRem - 1);
        }
        changedTicksRem = changedTicksLeft.getOrDefault(type, 0);

        lastValues.put(type, current);

        boolean needsRegen = current < max;

        if (max < 1) {
            return;
        }
        int tenth = max / 10;

        int X_SPACING = 8;

        int halfSpacing = type.getSide()
            .equals(Side.LEFT) ? 0 : +5;

        for (int i = 0; i < 10; i++) {

            int randomY = 0;

            if (changedTicksRem > 0) {
                randomY = mc.player.getRandom()
                    .nextInt(3) - 1;
            }

            drawTexture(matrix, x, y + randomY, 16, type.yPosTexture(data), 9, 9); // empty background

            if (current > 0) {
                if (current >= tenth) { // fullbar
                    drawTexture(matrix, x, y + randomY, 0, type.yPosTexture(data), 9, 9);
                } else { // half
                    drawTexture(matrix, x + halfSpacing, y + randomY, 10, type.yPosTexture(data), 5, 9);
                }
            }
            if (type.getSide()
                .equals(Side.LEFT)) {
                x += X_SPACING;
            } else {
                x -= X_SPACING;
            }

            current -= tenth;

        }

        RenderSystem.disableBlend();

    }

}