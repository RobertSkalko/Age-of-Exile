package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BackpackScreen extends HandledScreen<BackpackContainer> {
    private Identifier texture = new Identifier(Ref.MODID, "textures/gui/backpack/background.png");

    public BackpackScreen(BackpackContainer handler, PlayerInventory inventory, Text text) {
        super(handler, inventory, new LiteralText(""));

        backgroundWidth = 176;
        backgroundHeight = 207;

        this.texture = new Identifier(Ref.MODID, "textures/gui/backpack/" + handler.tier.tier + ".png");
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrices, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        // dont draw the damn name
    }

}
