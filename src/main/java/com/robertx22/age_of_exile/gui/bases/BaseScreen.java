package com.robertx22.age_of_exile.gui.bases;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class BaseScreen extends Screen {

    protected BaseScreen(int width, int height) {
        super(new LiteralText(""));
        this.sizeX = width;
        this.sizeY = height;
    }

    public MinecraftClient mc = MinecraftClient.getInstance();

    public int guiLeft = 0;
    public int guiTop = 0;

    public int sizeX = 0;
    public int sizeY = 0;

    public void renderBackground(MatrixStack matrix, Identifier id) {
        mc.getTextureManager()
            .bindTexture(id);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeybindsRegister.HUB_SCREEN_KEY.matchesKey(keyCode, scanCode)) {
            MinecraftClient.getInstance()
                .openScreen(null);
            OnKeyPress.cooldown = 5;
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;
    }

    public <T extends AbstractButtonWidget> T publicAddButton(T w) {
        return this.addButton(w);
    }

}
