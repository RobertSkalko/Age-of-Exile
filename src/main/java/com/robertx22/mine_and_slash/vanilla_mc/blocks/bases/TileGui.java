package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.RequestTilePacket;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.MutableText;

public abstract class TileGui<T extends BaseTileContainer, Tile extends BaseTile> extends HandledScreen<T> {

    public Tile tile;
    MinecraftClient mc;

    public TextRenderer font = MinecraftClient.getInstance().textRenderer;

    public TileGui(T cont, PlayerInventory inv, MutableText text, Class<Tile> token) {
        super(cont, inv, text);

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
}
