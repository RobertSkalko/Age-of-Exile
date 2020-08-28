package com.robertx22.age_of_exile.vanilla_mc.blocks.furnace;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.gui.buttons.HelpButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GuiUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.TileGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

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

        // If hoveringText is not empty draw the hovering text
        if (!hoveringText.isEmpty()) {

            // renderTooltip(hoveringText, mouseX - x, mouseY - y, font); todo
        }
    }

}