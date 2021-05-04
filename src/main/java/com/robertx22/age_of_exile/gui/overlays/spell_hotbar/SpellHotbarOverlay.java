package com.robertx22.age_of_exile.gui.overlays.spell_hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
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

    private static final Identifier TECHNIQUE_CAN_ACTIVATE = new Identifier(Ref.MODID,
        "textures/gui/spells/charged.png"
    );
    private static final Identifier TECHNIQUE_CANT_ACTIVATE = new Identifier(Ref.MODID,
        "textures/gui/spells/not_charged.png"
    );
    private static final Identifier AURA_ACTIVATED = new Identifier(Ref.MODID,
        "textures/gui/spells/aura_activated.png"
    );
    private static final Identifier SPELL_ON_COOLDOWN = new Identifier(Ref.MODID,
        "textures/gui/spells/on_cooldown.png"
    );

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
            data = Load.spells(mc.player);

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
        SpellCastContext ctx = null;
        try {
            spell = Load.spells(this.mc.player)
                .getSpellByNumber(place);

            ctx = new SpellCastContext(mc.player, 0, spell);
        } catch (Exception e) {
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

        int xs = (int) (x * 1 / scale);
        int ys = (int) (y * 1 / scale);

        if (data.getCastingData().auras.getOrDefault(spell.GUID(), new SpellCastingData.AuraData()).active) {
            mc.getTextureManager()
                .bindTexture(AURA_ACTIVATED);
        } else if (Load.Unit(mc.player)
            .getCooldowns()
            .isOnCooldown(spell.GUID())) {
            mc.getTextureManager()
                .bindTexture(SPELL_ON_COOLDOWN);
        } else if (!Load.Unit(this.mc.player)
            .getResources()
            .hasEnough(spell.getManaCostCtx(ctx))) {
            mc.getTextureManager()
                .bindTexture(SPELl_NO_MANA);
        } else if (spell.config.isTechnique() && !Load.spells(mc.player)
            .getCastingData()
            .meetActionRequirements(spell)) {
            mc.getTextureManager()
                .bindTexture(TECHNIQUE_CANT_ACTIVATE);
        } else if (spell.config.isTechnique() && Load.spells(mc.player)
            .getCastingData()
            .meetActionRequirements(spell)) {
            mc.getTextureManager()
                .bindTexture(TECHNIQUE_CAN_ACTIVATE);
        } else {
            mc.getTextureManager()
                .bindTexture(SPELL_READY_TEX);
        }

        this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

        if (spell != null) {
            mc.getTextureManager()
                .bindTexture(spell.getIconLoc());
            this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

            CooldownsData cds = Load.Unit(mc.player)
                .getCooldowns();

            float percent = (float) cds.getCooldownTicks(spell.GUID()) / (float) spell.config.cooldown_ticks;
            percent = MathHelper.clamp(percent, 0, 1F);
            mc.getTextureManager()
                .bindTexture(COOLDOWN_TEX);
            this.drawTexture(matrix, xs, ys, 0, 0, 32, (int) (32 * percent), 32, 32);

            int cdsec = cds.getCooldownTicks(spell.GUID()) / 20;
            if (cdsec > 1) {
                String stext = cdsec + "s";
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, stext, xs + 35, ys + 15, Formatting.YELLOW.getColorValue());
            }

            String txt = CLOC.translate(KeybindsRegister.getSpellHotbar(place)
                .getBoundKeyLocalizedText())
                .toUpperCase(Locale.ROOT);

            GuiUtils.renderScaledText(matrix,
                xs + 23, ys + 23, 1.4F, txt, Formatting.GREEN);

        }

        RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale);

    }

    private void renderHotbar(MatrixStack matrix, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
            .bindTexture(HOTBAR_TEX);

        this.drawTexture(matrix, x, y, 0, 0, WIDTH, HEIGHT);

    }

}