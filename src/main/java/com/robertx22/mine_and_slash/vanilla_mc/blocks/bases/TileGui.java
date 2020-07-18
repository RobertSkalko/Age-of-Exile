package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.vanilla_mc.packets.RequestTilePacket;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public abstract class TileGui<T extends BaseTileContainer, Tile extends BaseTile> extends ContainerScreen<T> {

    public Tile tile;
    MinecraftClient mc;

    public TileGui(T cont, PlayerInventory inv, Text text, Class<Tile> token) {
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
    public void render(int mouseX, int mouseY, float partialTicks) {

        if (tile != null) {
            if (mc.player.age % 3 == 0) {
                MMORPG.sendToServer(new RequestTilePacket(tile.getPos()));
            }
        }

        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(mouseX, mouseY);

    }
}
