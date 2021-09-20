package com.robertx22.age_of_exile.gui.screens.wiki;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class ExtraInfoButton extends ImageButton {

    public static int xSize = 20;
    public static int ySize = 20;

    static ResourceLocation buttonLoc = new ResourceLocation(SlashRef.MODID, "textures/gui/wiki/extra_info_button.png");

    Minecraft mc = Minecraft.getInstance();

    List<ITextComponent> list = new ArrayList<>();

    public ExtraInfoButton(List<ITextComponent> list, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {
        });
        this.list = list;
    }

    public ExtraInfoButton(List<ITextComponent> list, int xPos, int yPos, IPressable action) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, action);
        this.list = list;
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            GuiUtils.renderTooltip(matrix, list, x, y);
        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, xSize, ySize, x, y);
    }

}
