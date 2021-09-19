package com.robertx22.age_of_exile.gui.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class FavorButton extends ImageButton {

    public static int FAVOR_BUTTON_SIZE_X = 34;
    public static int FAVOR_BUTTON_SIZE_Y = 34;

    Minecraft mc = Minecraft.getInstance();

    public FavorButton(int xPos, int yPos) {
        super(xPos, yPos, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y, 0, 0, FAVOR_BUTTON_SIZE_Y, new ResourceLocation("empty"), (button) -> {
        });

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<ITextComponent> tooltip = new ArrayList<>();

            FavorRank rank = Load.playerRPGData(mc.player).favor
                .getRank();

            tooltip.add(Words.Favor.locName()
                .append(": " + (int) Load.playerRPGData(mc.player).favor
                    .getFavor())
                .withStyle(TextFormatting.WHITE)
                .withStyle(TextFormatting.BOLD));

            tooltip.add(new StringTextComponent(""));

            tooltip.addAll(rank.getTooltip());

            GuiUtils.renderTooltip(matrix,
                tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {

        ResourceLocation TEX = Load.playerRPGData(mc.player).favor
            .getRank()
            .getTexture();

        mc.getTextureManager()
            .bind(TEX);
        blit(matrix, this.x, this.y, 0, 0, this.width, this.height, width, height);

    }

}