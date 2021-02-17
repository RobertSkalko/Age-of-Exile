package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.tile_bases.TileGui;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GuiGearSalvage extends TileGui<ContainerGearSalvage, TileGearSalvage> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/salvage_station.png");

    public GuiGearSalvage(ContainerGearSalvage cont, PlayerInventory invPlayer,
                          MutableText comp) {
        super(cont, invPlayer, new LiteralText("Salvage"), TileGearSalvage.class);

        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    // some [x,y] coordinates of graphical elements
    final int COOK_BAR_XPOS = 49;
    final int COOK_BAR_YPOS = 60;
    final int COOK_BAR_ICON_U = 0; // texture position of white arrow icon
    final int COOK_BAR_ICON_V = 207;
    final int COOK_BAR_WIDTH = 80;
    final int COOK_BAR_HEIGHT = 17;

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Here you can salvage gears with Age of Exile stats."));
        list.add(new LiteralText("Gears are turned into scraps, which is used to fuel gear."));

        list.add(new LiteralText("There are some secret recipes."));

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth, this.y));
        this.addButton(new CraftButton(tile.getPos(), this.x + this.backgroundWidth / 2 - BUTTON_SIZE_X / 2, this.y + 35));

    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

        // get cook progress as a double between 0 and 1
        double cookProgress = tile.fractionOfCookTimeComplete();
        // draw the cook progress bar
        drawTexture(matrix, this.x + COOK_BAR_XPOS, this.y + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V, (int) (cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    @Override
    protected void drawForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.drawForeground(matrix, mouseX, mouseY);

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/craftbutton.png");
    static int BUTTON_SIZE_X = 61;
    static int BUTTON_SIZE_Y = 20;

    public class CraftButton extends TexturedButtonWidget {
        BlockPos pos;

        public CraftButton(BlockPos pos, int xPos, int yPos) {
            super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
                Packets.sendToServer(new ModifyItemPacket(pos));
            });
            this.pos = pos;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                try {
                    List<Text> tooltip = new ArrayList<>();

                    if (tooltip.isEmpty()) {
                        tooltip.add(new LiteralText("Salvage Item"));
                    }

                    GuiUtils.renderTooltip(matrix,
                        tooltip, x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
        }

    }

}