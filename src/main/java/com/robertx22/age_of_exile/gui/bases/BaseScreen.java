package com.robertx22.age_of_exile.gui.bases;

import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.LiteralText;

public class BaseScreen extends Screen {

    protected BaseScreen(int width, int height) {
        super(new LiteralText(""));
        this.sizeX = width;
        this.sizeY = height;
    }

    public int guiLeft = 0;
    public int guiTop = 0;

    public int sizeX = 0;
    public int sizeY = 0;

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
