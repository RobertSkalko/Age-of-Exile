package com.robertx22.age_of_exile.vanilla_mc.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.Ref;
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

    public int backgroundCanvasSize = 256;
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

    public int putInTheMiddleX(int buttnowidth) {

        int middle = this.x + (this.backgroundWidth / 2);

        return middle - (buttnowidth / 2);

    }

    static Identifier COOK_ARROW = Ref.id("textures/gui/cook_arrow.png");
    static int COOK_X = 24;
    static int COOK_Y = 17;

    public void renderCookArrow(MatrixStack matrix, int x, int y, float progress) {
        mc.getTextureManager()
            .bindTexture(COOK_ARROW);
        drawTexture(matrix, x, y, 0, 0, (int) (COOK_X * progress), COOK_Y);
    }

    static Identifier COOK_FLAME = Ref.id("textures/gui/cook_flame.png");
    static int FLAME_X = 14;
    static int FLAME_Y = 14;

    public void renderCookFlame(MatrixStack matrix, int x, int y, float progress) {
        mc.getTextureManager()
            .bindTexture(COOK_FLAME);

        int offset = (int) (FLAME_Y * (1F - progress));
        drawTexture(matrix, x, y + offset, 0, offset, FLAME_X, (int) (FLAME_Y * progress));

    }

    static Identifier ALCHEMY_ARROW = Ref.id("textures/gui/alchemy_arrow.png");
    static int A_ARROW_X = 9;
    static int A_ARROW_Y = 28;

    public void renderAlchemyArrow(MatrixStack matrix, int x, int y, float progress) {
        mc.getTextureManager()
            .bindTexture(ALCHEMY_ARROW);

        drawTexture(matrix, x, y, 0, 0, A_ARROW_X, (int) (A_ARROW_Y * progress));

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
        renderOther(matrix, mouseX, mouseY);
        this.drawMouseoverTooltip(matrix, mouseX, mouseY);

    }

    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {

    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(background);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight, backgroundCanvasSize, backgroundCanvasSize);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

}

