package com.robertx22.mine_and_slash.gui.screens.spell_hotbar_setup;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.gui.buttons.HelpButton;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SpellHotbatSetupScreen extends HandledScreen<HotbarSetupContainer> {

    static Identifier BACKGROUND_TEXTURE = new Identifier(
        Ref.MODID, "textures/gui/hotbar_setup/window.png");
    public MinecraftClient mc;

    static int sizeX = 176;
    static int sizeY = 166;

    PlayerSpellCap.ISpellsCap spells;

    public int guiLeft = 0;
    public int guiTop = 0;

    public SpellHotbatSetupScreen(HotbarSetupContainer container, PlayerEntity player) {
        super(container, player.inventory, new LiteralText(""));
        this.mc = MinecraftClient.getInstance();
        this.spells = Load.spells(mc.player);

    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Put spells here so they appear in your hotbar."));
        list.add(new LiteralText(""));
        list.add(new LiteralText("Each spell requires different weapon to cast."));
        list.add(new LiteralText("Mage weapon means a staff, wand or scepter."));

        list.add(new LiteralText(""));
        list.add(new LiteralText("To Choose a spell, press [R], then select it."));
        list.add(new LiteralText("To cast it, right click with the correct weapon."));
        list.add(new LiteralText("If the weapon is like a bow, do [Shift] + [RMB]"));

        this.addButton(new HelpButton(list, guiLeft + sizeX - 30, guiTop + 10));

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            super.render(matrix, x, y, ticks);
            this.drawMouseoverTooltip(matrix, x, y);

            this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

            double scale = 1;

            String str = "Spell Hotbar Setup";
            int xp = (int) (guiLeft + (SpellHotbatSetupScreen.sizeX / 3));
            int yp = guiTop + 15;
            GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.GREEN);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void drawBackground(MatrixStack matrix, float delta, int mouseX, int mouseY) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, guiLeft, guiTop, this.getZOffset(), 0.0F, 0.0F, sizeX, sizeY, 256, 256);

    }

}

