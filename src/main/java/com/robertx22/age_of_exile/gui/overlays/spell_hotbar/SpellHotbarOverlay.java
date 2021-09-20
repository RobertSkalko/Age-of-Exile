package com.robertx22.age_of_exile.gui.overlays.spell_hotbar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.util.Locale;

public class SpellHotbarOverlay extends AbstractGui {

    private static final ResourceLocation HOTBAR_TEX = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/hotbar.png"
    );
    private static final ResourceLocation COOLDOWN_TEX = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/cooldown.png"
    );
    private static final ResourceLocation SPELL_READY_TEX = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/spell_ready.png"
    );
    private static final ResourceLocation SPELl_NO_MANA = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/no_mana.png"
    );

    private static final ResourceLocation SPELL_ON_COOLDOWN = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/on_cooldown.png"
    );

    private static final ResourceLocation CHARGE = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/charge_icon.png"
    );

    int CHARGE_SIZE = 9;

    static int WIDTH = 22;
    static int HEIGHT = 82;

    Minecraft mc = Minecraft.getInstance();

    EntitySpellCap.ISpellsCap data;


    public void onHudRender(MatrixStack matrix, float v) {


        try {
            if (mc.options.renderDebug || mc.player.isSpectator()) {
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
                    .getGuiScaledHeight() / 2 - HEIGHT / 2;

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

        int x = 3;
        int y = mc.getWindow()
                .getGuiScaledHeight() / 2 - HEIGHT / 2 + 3;

        y += num * 20;

        try {
            int xs = (int) (x);
            int ys = (int) (y);

            if (Load.Unit(mc.player)
                    .getCooldowns()
                    .getCooldownTicks(spell.GUID()) > 1) {
                mc.getTextureManager()
                        .bind(SPELL_ON_COOLDOWN);
            } else {
                mc.getTextureManager()
                        .bind(SPELL_READY_TEX);
            }

            this.blit(matrix, xs, ys, 0, 0, 16, 16, 16, 16);

            if (spell != null) {
                mc.getTextureManager()
                        .bind(spell.getIconLoc());
                this.blit(matrix, xs, ys, 0, 0, 16, 16, 16, 16);

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

                    mc.getTextureManager()
                            .bind(CHARGE);
                    int chargex = x + 21;

                    for (int i = 0; i < charges; i++) {
                        this.blit(matrix, chargex, y + 5, 0, 0, CHARGE_SIZE, CHARGE_SIZE, CHARGE_SIZE, CHARGE_SIZE);
                        chargex += CHARGE_SIZE + 1;
                    }

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
                        GuiUtils.renderScaledText(matrix, xs + 27, ys + 10, 0.75F, stext, TextFormatting.YELLOW);
                    }
                }

                String txt = CLOC.translate(KeybindsRegister.getSpellHotbar(place)
                                .getTranslatedKeyMessage())
                        .toUpperCase(Locale.ROOT);

                if (txt.length() > 3) {
                    txt = txt.substring(0, 2);
                }
                GuiUtils.renderScaledText(matrix,
                        xs + 14, ys + 12, 1, txt, TextFormatting.GREEN);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void drawCooldown(float percent, MatrixStack matrix, int x, int y) {

        mc.getTextureManager()
                .bind(COOLDOWN_TEX);
        this.blit(matrix, x, y, 0, 0, 16, (int) (16 * percent), 16, 16);
    }

    private void renderHotbar(MatrixStack matrix, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
                .bind(HOTBAR_TEX);

        this.blit(matrix, x, y, 0, 0, WIDTH, HEIGHT);

    }

}