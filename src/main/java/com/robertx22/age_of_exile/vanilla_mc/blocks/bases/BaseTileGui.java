package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.age_of_exile.mmorpg.Packets;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.age_of_exile.vanilla_mc.packets.RequestTilePacket;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public abstract class BaseTileGui<Tile extends BlockEntity> extends Screen {

    public Tile tile;
    public MinecraftClient mc;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;
    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;

    protected int xSize = 176;
    protected int ySize = 166;

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

    }

    public BaseTileGui(Class<Tile> token, BlockPos pos, int x, int y) {
        super(new SText(""));

        xSize = x;
        ySize = y;

        this.mc = MinecraftClient.getInstance();

        if (pos != null) {
            BlockEntity en = MinecraftClient.getInstance().world.getBlockEntity(pos);
            if (en != null) {
                if (token.isAssignableFrom(en.getClass())) {
                    this.tile = (Tile) en;
                }
            }
        }
    }

    int ticks;

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {

        ticks++;

        if (tile != null) {
            if (ticks % 10 == 0 || ticks < 2) {
                Packets.sendToServer(new RequestTilePacket(tile.getPos()));
            }
        }

        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);

    }

}
