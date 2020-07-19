package com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.bases.TileGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GuiGearModify extends TileGui<ContainerGearModify, TileGearModify> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/modify_station.png");

    public GuiGearModify(ContainerGearModify cont, PlayerInventory invPlayer, Text comp) {
        super(cont, invPlayer, comp, TileGearModify.class);

        containerWidth = 256;
        containerHeight = 207;

    }

    // some [x,y] coordinates of graphical elements
    final int COOK_BAR_XPOS = 49;
    final int COOK_BAR_YPOS = 60;
    final int COOK_BAR_ICON_U = 0; // texture position of white arrow icon
    final int COOK_BAR_ICON_V = 207;
    final int COOK_BAR_WIDTH = 80;
    final int COOK_BAR_HEIGHT = 17;

    @Override
    protected void drawBackground(float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        blit(x, y, 0, 0, containerWidth, containerHeight);

        // draw the cook progress bar
        blit(
            x + 85, y + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
            (int) (this.tile.fractionOfCookTimeComplete() * COOK_BAR_WIDTH), COOK_BAR_HEIGHT
        );

    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);

        LocReqContext context = tile.getLocReqContext();
        if (context.effect != null && context.hasStack()) {
            int y = 80;

            boolean add = true;

            for (BaseLocRequirement req : context.effect.requirements()) {
                if (req.isNotAllowed(context)) {
                    String txt = CLOC.translate(req.getText());

                    if (add) {
                        String reqtext = CLOC.translate(Words.RequirementsNotMet.locName()) + ": ";

                        font.draw(
                            reqtext, this.containerWidth / 2 - font.getStringWidth(reqtext) / 2, y, Color.red.getRGB());
                        y += font.fontHeight + 1;
                        add = false;
                    }

                    font.draw(txt, this.containerWidth / 2 - font.getStringWidth(txt) / 2, y, Color.red.getRGB());
                    y += font.fontHeight;
                }
            }

        }

        final int LABEL_XPOS = 5;
        final int LABEL_YPOS = 5;
        font.draw(CLOC.translate(tile.getDisplayName()), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

        List<String> hoveringText = new ArrayList<String>();

        // If the mouse is over the progress bar add the progress bar hovering text
        if (GuiUtils.isInRect(
            x + COOK_BAR_XPOS, y + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)) {
            hoveringText.add(Words.Progress.translate() + ": ");
            int cookPercentage = (int) (this.tile.fractionOfCookTimeComplete() * 100);
            hoveringText.add(cookPercentage + "%");
        }

        // If hoveringText is not empty draw the hovering text
        if (!hoveringText.isEmpty()) {

            // TODO renderTooltip(hoveringText, mouseX - x, mouseY - y, font);
        }
    }

}