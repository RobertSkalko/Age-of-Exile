package com.robertx22.age_of_exile.gui.screens.spell;

import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LeftRightButton extends TexturedButtonWidget {

    public static Identifier TEX = Ref.guiId("leftright/leftright");

    public static int xSize = 22;
    public static int ySize = 22;

    public LeftRightButton(ILeftRight screen, int xPos, int yPos, boolean isLeft) {
        super(xPos, yPos, xSize, ySize, isLeft ? 0 : 22, 0, 0, TEX, (button) -> {
            int times = 1;

            for (int i = 0; i < times; i++) {
                if (isLeft) {
                    screen.goLeft();
                } else {
                    screen.goRight();
                }
            }
        });
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);
    }

}