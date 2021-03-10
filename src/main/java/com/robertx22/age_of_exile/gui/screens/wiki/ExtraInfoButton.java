package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ExtraInfoButton extends TexturedButtonWidget {

    public static int xSize = 20;
    public static int ySize = 20;

    static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/wiki/extra_info_button.png");

    MinecraftClient mc = MinecraftClient.getInstance();

    List<Text> list = new ArrayList<>();

    public ExtraInfoButton(List<Text> list, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {
        });
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
