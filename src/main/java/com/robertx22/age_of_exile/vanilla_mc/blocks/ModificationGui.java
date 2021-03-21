package com.robertx22.age_of_exile.vanilla_mc.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.RequestTilePacket;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public abstract class ModificationGui<T extends BaseTileContainer, Tile extends BaseModificationStation> extends HandledScreen<T> {

    public Tile tile;
    MinecraftClient mc;

    public TextRenderer font = MinecraftClient.getInstance().textRenderer;
    Identifier background;

    public ModificationGui(Identifier background, T cont, PlayerInventory inv, MutableText text, Class<Tile> token) {
        super(cont, inv, text);
        this.background = background;
        this.mc = MinecraftClient.getInstance();

        if (cont.pos != null) {
            BlockEntity en = MinecraftClient.getInstance().world.getBlockEntity(cont.pos);
            if (en != null) {
                if (token.isAssignableFrom(en.getClass())) {
                    this.tile = (Tile) en;
                }
            }
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        // dont draw "inventory" text lol
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {

        if (tile != null) {
            if (mc.player.age % 3 == 0) {
                Packets.sendToServer(new RequestTilePacket(tile.getPos()));
            }
        }

        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(matrix, mouseX, mouseY);

    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(background);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }
}

