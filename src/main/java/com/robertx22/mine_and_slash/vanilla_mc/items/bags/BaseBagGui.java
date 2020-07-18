package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public abstract class BaseBagGui<T extends Container> extends ContainerScreen<T> {

    public BaseBagGui(PlayerInventory inv, T inventorySlotsIn) {
        super(inventorySlotsIn, inv, new LiteralText(""));

        this.containerWidth = BaseBagGui.bagXSize;
        this.containerHeight = BaseBagGui.bagYSize;
    }

    public BaseBagGui(PlayerInventory inv, T inventorySlotsIn, int sizex, int sizey) {
        super(inventorySlotsIn, inv, new LiteralText(""));

        this.containerWidth = sizex;
        this.containerHeight = sizey;
    }

    public static final int bagXSize = 176;
    public static final int bagYSize = 207;

    public abstract Identifier texture();

    public abstract int rows();

    public abstract String name();

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();

        super.render(mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        this.font.draw(name(), 8, 6, 4210752);
    }

    @Override
    protected void drawBackground(float partialTicks, int mouseX,
                                                   int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(texture());
        int i = (this.width - this.containerWidth) / 2;
        int j = (this.height - this.containerHeight) / 2;
        this.blit(i, j, 0, 0, this.containerWidth, rows() * 18 + 17);
        this.blit(i, j + rows() * 18 + 17, 0, 126, this.containerWidth, 96);
    }

}