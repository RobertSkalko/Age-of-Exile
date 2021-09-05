package com.robertx22.age_of_exile.gui.overlays.spell_hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Locale;

public class SpellHotbarOverlay extends DrawableHelper implements HudRenderCallback {

    private static final Identifier HOTBAR_TEX = new Identifier(Ref.MODID,
        "textures/gui/spells/hotbar.png"
    );
    private static final Identifier COOLDOWN_TEX = new Identifier(Ref.MODID,
        "textures/gui/spells/cooldown.png"
    );
    private static final Identifier SPELL_READY_TEX = new Identifier(Ref.MODID,
        "textures/gui/spells/spell_ready.png"
    );
    private static final Identifier SPELl_NO_MANA = new Identifier(Ref.MODID,
        "textures/gui/spells/no_mana.png"
    );
    private static final Identifier AURA_ACTIVATED = new Identifier(Ref.MODID,
        "textures/gui/spells/aura_activated.png"
    );
    private static final Identifier SPELL_ON_COOLDOWN = new Identifier(Ref.MODID,
        "textures/gui/spells/on_cooldown.png"
    );

    private static final Identifier CHARGE = new Identifier(Ref.MODID,
        "textures/gui/spells/charge_icon.png"
    );

    int CHARGE_SIZE = 9;

    static int WIDTH = 22;
    static int HEIGHT = 82;

    MinecraftClient mc = MinecraftClient.getInstance();

    EntitySpellCap.ISpellsCap data;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {
            if (mc.options.debugEnabled || mc.player.isSpectator()) {
                return;
            }
            if (mc.player.isSpectator()) {
                return;
            }
            if (data == null) {
                data = Load.spells(mc.player);
            }
            if (ChatUtils.isChatOpen()) {
                return;
            }

            RenderSystem.enableBlend(); // enables transparency

            int x = 0;
            int y = mc.getWindow()
                .getScaledHeight() / 2 - HEIGHT / 2;

            renderHotbar(matrix, x, y);
            //renderSpellsOnHotbar(matrix, x, y);

            for (int i = 0; i < 4; i++) {

                int place = i;
                if (Screen.hasShiftDown()) {
                    place += 4;
                }

                RenderSystem.enableBlend(); // enables transparency
                renderCurrentSpell(place, i, matrix);
                RenderSystem.disableBlend(); // enables transparency

            }
            RenderSystem.disableBlend(); // enables transparency
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renderCurrentSpell(int place, int num, MatrixStack matrix) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        boolean render = true;

        Spell spell = null;
        try {
            spell = Load.spells(this.mc.player)
                .getSpellByNumber(place);

            if (spell == null) {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            render = false;
        }

        if (!render) {
            return;
        }

        int x = 2;
        int y = mc.getWindow()
            .getScaledHeight() / 2 - HEIGHT / 2 + 2;

        y += num * 20;

        double scale = 0.6F;
        RenderSystem.scaled(scale, scale, scale);

        try {
            int xs = (int) (x * 1 / scale);
            int ys = (int) (y * 1 / scale);

            if (data.getCastingData().auras.getOrDefault(spell.GUID(), new SpellCastingData.AuraData()).active) {
                mc.getTextureManager()
                    .bindTexture(AURA_ACTIVATED);
            } else if (Load.Unit(mc.player)
                .getCooldowns()
                .getCooldownTicks(spell.GUID()) > 1) {
                mc.getTextureManager()
                    .bindTexture(SPELL_ON_COOLDOWN);
            } else {
                mc.getTextureManager()
                    .bindTexture(SPELL_READY_TEX);
            }

            this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

            if (spell != null) {
                mc.getTextureManager()
                    .bindTexture(spell.getIconLoc());
                this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

                if (spell.config.charges > 0) {
                    int charges = data.getCastingData().charges.getCharges(spell.config.charge_name);

                    if (charges == 0) {
                        float needed = (float) spell.config.charge_regen;
                        float currentticks = (float) data.getCastingData().charges.getCurrentTicksChargingOf(spell.config.charge_name);

                        float ticksleft = needed - currentticks;

                        float percent = ticksleft / needed;
                        percent = MathHelper.clamp(percent, 0, 1F);
                        drawCooldown(percent, matrix, xs, ys);

                    }

                    RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale);

                    mc.getTextureManager()
                        .bindTexture(CHARGE);
                    int chargex = x + 21;

                    for (int i = 0; i < charges; i++) {
                        this.drawTexture(matrix, chargex, y + 5, 0, 0, CHARGE_SIZE, CHARGE_SIZE, CHARGE_SIZE, CHARGE_SIZE);
                        chargex += CHARGE_SIZE + 1;
                    }

                    RenderSystem.scaled(scale, scale, scale);

                } else {

                    CooldownsData cds = Load.Unit(mc.player)
                        .getCooldowns();

                    float percent = (float) cds.getCooldownTicks(spell.GUID()) / (float) cds.getNeededTicks(spell.GUID());
                    if (cds.getCooldownTicks(spell.GUID()) > 1) {
                        percent = MathHelper.clamp(percent, 0, 1F);
                        drawCooldown(percent, matrix, xs, ys);
                    }

                    int cdsec = cds.getCooldownTicks(spell.GUID()) / 20;
                    if (cdsec > 1) {
                        String stext = cdsec + "s";
                        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, stext, xs + 35, ys + 15, Formatting.YELLOW.getColorValue());
                    }
                }

                String txt = CLOC.translate(KeybindsRegister.getSpellHotbar(place)
                        .getBoundKeyLocalizedText())
                    .toUpperCase(Locale.ROOT);

                if (txt.length() > 3) {
                    txt = txt.substring(0, 2);
                }
                GuiUtils.renderScaledText(matrix,
                    xs + 23, ys + 23, 1.4F, txt, Formatting.GREEN);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale);

    }

    private void drawCooldown(float percent, MatrixStack matrix, int x, int y) {

        mc.getTextureManager()
            .bindTexture(COOLDOWN_TEX);
        this.drawTexture(matrix, x, y, 0, 0, 32, (int) (32 * percent), 32, 32);
    }

    private void renderHotbar(MatrixStack matrix, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
            .bindTexture(HOTBAR_TEX);

        this.drawTexture(matrix, x, y, 0, 0, WIDTH, HEIGHT);

    }

}