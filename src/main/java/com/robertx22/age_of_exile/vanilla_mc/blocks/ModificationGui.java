package com.robertx22.age_of_exile.vanilla_mc.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.RequestTilePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

public abstract class ModificationGui<T extends BaseTileContainer, Tile extends BaseModificationStation> extends ContainerScreen<T> {

    public Tile tile;
    public Minecraft mc;

    public int backgroundCanvasSize = 256;
    public FontRenderer font = Minecraft.getInstance().font;
    ResourceLocation background;

    public ModificationGui(ResourceLocation background, T cont, PlayerInventory inv, IFormattableTextComponent text, Class<Tile> token) {
        super(cont, inv, text);
        this.background = background;
        this.mc = Minecraft.getInstance();

        if (cont.pos != null) {
            TileEntity en = Minecraft.getInstance().level.getBlockEntity(cont.pos);
            if (en != null) {
                if (token.isAssignableFrom(en.getClass())) {
                    this.tile = (Tile) en;
                }
            }
        }

        if (tile == null) {
            this.onClose();
        }

    }

    public int putInTheMiddleX(int buttnowidth) {

        int middle = this.leftPos + (this.imageWidth / 2);

        return middle - (buttnowidth / 2);

    }

    static ResourceLocation COOK_ARROW = SlashRef.id("textures/gui/cook_arrow.png");
    static int COOK_X = 24;
    static int COOK_Y = 17;

    public void renderCookArrow(MatrixStack matrix, int x, int y, float progress) {
        mc.getTextureManager()
            .bind(COOK_ARROW);
        blit(matrix, x, y, 0, 0, (int) (COOK_X * progress), COOK_Y);
    }

    static ResourceLocation COOK_FLAME = SlashRef.id("textures/gui/cook_flame.png");
    static int FLAME_X = 14;
    static int FLAME_Y = 14;

    public void renderCookFlame(MatrixStack matrix, int x, int y, float progress) {
        mc.getTextureManager()
            .bind(COOK_FLAME);

        int offset = (int) (FLAME_Y * (1F - progress));
        blit(matrix, x, y + offset, 0, offset, FLAME_X, (int) (FLAME_Y * progress));

    }

    static ResourceLocation ALCHEMY_ARROW = SlashRef.id("textures/gui/alchemy_arrow.png");
    static int A_ARROW_X = 9;
    static int A_ARROW_Y = 28;

    public void renderAlchemyArrow(MatrixStack matrix, int x, int y, float progress) {
        mc.getTextureManager()
            .bind(ALCHEMY_ARROW);

        blit(matrix, x, y, 0, 0, A_ARROW_X, (int) (A_ARROW_Y * progress));

    }

    @Override
    protected void renderLabels(MatrixStack matrices, int mouseX, int mouseY) {
        // dont draw "inventory" text lol
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {

        if (tile != null) {
            if (mc.player.tickCount % 3 == 0) {
                Packets.sendToServer(new RequestTilePacket(tile.getBlockPos()));
            }
        }

        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        renderOther(matrix, mouseX, mouseY);
        this.renderTooltip(matrix, mouseX, mouseY);

    }

    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {

    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int x, int y) {

        // Bind the image texture
        Minecraft.getInstance()
            .getTextureManager()
            .bind(background);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        blit(matrix, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, backgroundCanvasSize, backgroundCanvasSize);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

}

