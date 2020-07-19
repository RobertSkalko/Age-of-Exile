package com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.bases.TileGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GuiGearRepair extends TileGui<ContainerGearRepair, TileGearRepair> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/repair_station.png");

    public GuiGearRepair(ContainerGearRepair cont, PlayerInventory invPlayer,
                         Text comp) {
        super(cont, invPlayer, new LiteralText("Modify"), TileGearRepair.class);

        // Set the width and height of the gui
        containerWidth = 176;
        containerHeight = 207;

    }

    // some [x,y] coordinates of graphical elements
    final int COOK_BAR_XPOS = 49;
    final int COOK_BAR_YPOS = 60;
    final int COOK_BAR_ICON_U = 0; // texture position of white arrow icon
    final int COOK_BAR_ICON_V = 207;
    final int COOK_BAR_WIDTH = 80;
    final int COOK_BAR_HEIGHT = 17;

    final int FLAME_XPOS = 81;// 54;
    final int FLAME_YPOS = 80;
    final int FLAME_ICON_U = 176; // texture position of flame icon
    final int FLAME_ICON_V = 0;
    final int FLAME_WIDTH = 14;
    final int FLAME_HEIGHT = 14;
    final int FLAME_X_SPACING = 18;

    @Override
    protected void drawBackground(float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(x, y, 0, 0, containerWidth, containerHeight);

        // get cook progress as a double between 0 and 1
        // draw the cook progress bar
        blit(x + COOK_BAR_XPOS, y + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V, (int) (tile
            .fractionOfCookTimeComplete() * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);

        // draw the fuel remaining bar for each fuel slot flame
        for (int i = 0; i < TileGearRepair.FUEL_SLOTS_COUNT; ++i) {
            //double burnRemaining = tileEntity.fractionOfFuelRemaining(i);

            int yOffset = (int) ((1 - (float) tile.fuel / tile.MaximumFuel) * FLAME_HEIGHT);
            blit(x + FLAME_XPOS + FLAME_X_SPACING * i, y + FLAME_YPOS + yOffset, FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);
        }

        // renderHoveredToolTip(x, y);

    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);

        final int LABEL_XPOS = 5;
        final int LABEL_YPOS = 5;
        font.draw(CLOC.translate(tile.getDisplayName()), LABEL_XPOS, LABEL_YPOS, Color.darkGray
            .getRGB());

        List<String> hoveringText = new ArrayList<String>();

        // If the mouse is over the progress bar add the progress bar hovering text
        if (GuiUtils.isInRect(x + COOK_BAR_XPOS, y + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)) {
            hoveringText.add(Words.Progress.translate() + ": ");
            int cookPercentage = (int) (tile.fractionOfCookTimeComplete() * 100);
            hoveringText.add(cookPercentage + "%");
        }

        // If the mouse is over one of the burn time indicator add the burn time
        // indicator hovering text
        for (int i = 0; i < TileGearRepair.FUEL_SLOTS_COUNT; ++i) {
            if (GuiUtils.isInRect(x + FLAME_XPOS + FLAME_X_SPACING * i, y + FLAME_YPOS, FLAME_WIDTH, FLAME_HEIGHT, mouseX, mouseY)) {
                // hoveringText.add("Fuel Time:");
                hoveringText.add(Words.Fuel.translate() + ": " + tile.fuel);
            }
        }
        // If hoveringText is not empty draw the hovering text
        if (!hoveringText.isEmpty()) {

            // TODO renderTooltip(hoveringText, mouseX - x, mouseY - y, font);
        }

    }

}