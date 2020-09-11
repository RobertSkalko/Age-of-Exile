package com.robertx22.age_of_exile.gui.overlays.spell_hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.mixin_methods.OnKeyMethod;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SpellHotbarOverlay extends DrawableHelper implements HudRenderCallback {

    private static final Identifier HOTBAR_TEX = new Identifier(Ref.MODID,
        "textures/gui/spells/hotbar_horizontal.png"
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
    private static final Identifier SPELL_ON_COOLDOWN = new Identifier(Ref.MODID,
        "textures/gui/spells/on_cooldown.png"
    );

    static int WIDTH = 182;
    static int HEIGHT = 22;

    MinecraftClient mc = MinecraftClient.getInstance();

    PlayerSpellCap.ISpellsCap data;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {
            if (mc.options.debugEnabled) {
                // return; // dont display when F3 screen
            }

            if (mc.player.isSpectator()) {
                return;
            }
            data = Load.spells(mc.player);

            RenderSystem.enableBlend(); // enables transparency

            if (OnKeyMethod.isSelectingSpells()) {
                int x = mc.getWindow()
                    .getScaledWidth() / 2 - WIDTH / 2;
                int y = (int) (mc.getWindow()
                    .getScaledHeight() - HEIGHT);

                renderHotbar(matrix, x, y);
                renderSpellsOnHotbar(matrix, x, y);

            }

            renderCurrentSpell(matrix);

            RenderSystem.disableBlend(); // enables transparency
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renderCurrentSpell(MatrixStack matrix) {

        boolean render = true;

        Spell spell = null;
        SpellCastContext ctx = null;
        try {
            spell = Load.spells(this.mc.player)
                .getCurrentRightClickSpell();

            ctx = new SpellCastContext(mc.player, 0, spell);
        } catch (Exception e) {
            render = false;
        }

        if (!render) {
            return;
        }

        int x = mc.getWindow()
            .getScaledWidth() / 2 + 98;
        int y = mc.getWindow()
            .getScaledHeight() - HEIGHT + 2;

        double scale = 0.6F;
        RenderSystem.scaled(scale, scale, scale);

        int xs = (int) (x * 1 / scale);
        int ys = (int) (y * 1 / scale);

        if (!this.data.getCastingData()
            .getDataBySpell(spell)
            .cooldownIsReady()) {
            mc.getTextureManager()
                .bindTexture(SPELL_ON_COOLDOWN);
        } else if (!Load.Unit(this.mc.player)
            .getResources()
            .hasEnough(spell.getManaCostCtx(ctx))) {
            mc.getTextureManager()
                .bindTexture(SPELl_NO_MANA);
        } else {
            mc.getTextureManager()
                .bindTexture(SPELL_READY_TEX);
        }

        this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

        if (spell != null) {
            mc.getTextureManager()
                .bindTexture(spell.getIconLoc());
            this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

            SpellData spelldata = Load.spells(mc.player)
                .getCastingData()
                .getDataBySpell(spell);

            if (spelldata != null) {
                float percent = (float) spelldata.getRemainingCooldown() / (float) spelldata.getTotalCooldown();
                percent = MathHelper.clamp(percent, 0, 1F);
                mc.getTextureManager()
                    .bindTexture(COOLDOWN_TEX);
                this.drawTexture(matrix, xs, ys, 0, 0, 32, (int) (32 * percent), 32, 32);
            }

        }

        RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale);

    }

    private void renderSpellsOnHotbar(MatrixStack matrix, int x, int y) {

        x += 3;
        y += 3;

        for (int i = 0; i < 9; i++) {
            Spell spell = data.getSpellByNumber(i);

            boolean selected = i == SpellCastingData.selectedSpell;

            if (selected) {
                mc.getTextureManager()
                    .bindTexture(SPELL_READY_TEX);
                this.drawTexture(matrix, x - 2, y - 2, 0, 0, 20, 20, 20, 20);
            }

            if (spell != null) {

                double scale = 0.5D;
                RenderSystem.scaled(scale, scale, scale);

                int xs = (int) (x * 1 / scale);
                int ys = (int) (y * 1 / scale);

                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

                mc.getTextureManager()
                    .bindTexture(spell.getIconLoc());
                this.drawTexture(matrix, xs, ys, 0, 0, 32, 32, 32, 32);

                SpellData spelldata = data.getCastingData()
                    .getDataBySpell(spell);

                if (spelldata != null) {
                    if (spelldata.cooldownIsReady() == false) {

                        float percent = (float) spelldata.getRemainingCooldown() / (float) spelldata.getTotalCooldown();
                        percent = MathHelper.clamp(percent, 0, 1F);
                        mc.getTextureManager()
                            .bindTexture(COOLDOWN_TEX);
                        this.drawTexture(matrix, xs, ys, 0, 0, 32, (int) (32 * percent), 32, 32);

                    }
                }

                RenderSystem.scaled(1 / scale, 1 / scale, 1 / scale);

                if (spelldata != null) {
                    if (spelldata.cooldownIsReady()) {
                        if (OnClientTick.COOLDOWN_READY_MAP.getOrDefault(spell.GUID(), 0) > 0) {

                            RenderSystem.enableBlend(); // enables transparency
                            mc.getTextureManager()
                                .bindTexture(SPELL_READY_TEX);
                            this.drawTexture(matrix, x - 2, y - 2, 0, 0, 20, 20, 20, 20);
                            RenderSystem.disableBlend(); // enables transparency

                        }
                    }
                }
            }
            x += 20;
        }
    }

    private void renderHotbar(MatrixStack matrix, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
            .bindTexture(HOTBAR_TEX);

        this.drawTexture(matrix, x, y, 0, 0, WIDTH, HEIGHT);

    }

}