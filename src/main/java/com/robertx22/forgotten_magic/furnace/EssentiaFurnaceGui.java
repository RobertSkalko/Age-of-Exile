package com.robertx22.forgotten_magic.furnace;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.gui.buttons.HelpButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GuiUtils;
import com.robertx22.library_of_exile.tile_bases.TileGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EssentiaFurnaceGui extends TileGui<EssentiaFurnaceContainer, EssentiaFurnaceBlockEntity> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/essentia_furnace.png");

    public EssentiaFurnaceGui(EssentiaFurnaceContainer cont, PlayerInventory invPlayer,
                              MutableText comp) {
        super(cont, invPlayer, new LiteralText("Essentia Furnace"), EssentiaFurnaceBlockEntity.class);

        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    // some [x,y] coordinates of graphical elements
    final int COOK_BAR_XPOS = 78;
    final int COOK_BAR_YPOS = 56;
    final int COOK_BAR_ICON_U = 0; // texture position of white arrow icon
    final int COOK_BAR_ICON_V = 207;
    final int COOK_BAR_WIDTH = 24;
    final int COOK_BAR_HEIGHT = 18;

    final int FLAME_XPOS = 81;// 54;
    final int FLAME_YPOS = 74;
    final int FLAME_ICON_U = 176; // texture position of flame icon
    final int FLAME_ICON_V = 0;
    final int FLAME_WIDTH = 14;
    final int FLAME_HEIGHT = 14;
    final int FLAME_X_SPACING = 18;

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("This is a special type of furnace fueled by essences!"));

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth, this.y));

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

        // draw the fuel remaining bar for each fuel slot flame
        for (int i = 0; i < EssentiaFurnaceBlockEntity.FUEL_SLOTS.size(); ++i) {
            //double burnRemaining = tileEntity.fractionOfFuelRemaining(i);

            int yOffset = (int) ((1 - (float) tile.fuel / tile.MaximumFuel) * FLAME_HEIGHT);
            drawTexture(matrix, this.x + FLAME_XPOS + FLAME_X_SPACING * i, this.y + FLAME_YPOS + yOffset, FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);
        }
        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    @Override
    protected void drawForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.drawForeground(matrix, mouseX, mouseY);

        java.util.List<String> hoveringText = new ArrayList<String>();

        // If the mouse is over the progress bar add the progress bar hovering text
        if (GuiUtils.isInRect(x + COOK_BAR_XPOS, y + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)) {
            hoveringText.add(Words.Progress.translate() + ": ");
            int cookPercentage = (int) (tile.fractionOfCookTimeComplete() * 100);
            hoveringText.add(cookPercentage + "%");
        }

        for (int i = 0; i < EssentiaFurnaceBlockEntity.FUEL_SLOTS.size(); ++i) {
            if (GuiUtils.isInRect(x + FLAME_XPOS + FLAME_X_SPACING * i, y + FLAME_YPOS, FLAME_WIDTH, FLAME_HEIGHT, mouseX, mouseY)) {
                hoveringText.add(Words.Fuel.translate() + ": " + tile.fuel);
            }
        }

        if (!hoveringText.isEmpty()) {
            renderTooltip(matrix, hoveringText.stream()
                .map(x -> new LiteralText(x))
                .collect(Collectors.toList()), mouseX - x, mouseY - y);
        }

    }

}