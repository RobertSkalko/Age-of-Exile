package com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class MatBagScreen extends ContainerScreen<MatBagContainer> {
    private ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/backpack/pouch.png");

    public MatBagScreen(MatBagContainer handler, PlayerInventory inventory, ITextComponent text) {
        super(handler, inventory, new StringTextComponent(""));

        imageWidth = 176;
        imageHeight = 222;
    }

    @Override
    protected void renderBg(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        Minecraft.getInstance()
            .getTextureManager()
            .bind(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrices, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.renderTooltip(matrices, mouseX, mouseY);
        buttons.forEach(x -> x.renderToolTip(matrices, mouseX, mouseY));
    }

    @Override
    protected void renderLabels(MatrixStack matrices, int mouseX, int mouseY) {
        // dont draw the damn name

    }

}
