package com.robertx22.age_of_exile.gui.screens.race_select;

import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LeftRightButton extends TexturedButtonWidget {

    public static int xSize = 22;
    public static int ySize = 22;

    public enum Type {
        ONE(Ref.guiId("leftright/leftright")),
        TEN(Ref.guiId("leftright/leftrightten"));

        public Identifier icon;

        Type(Identifier icon) {
            this.icon = icon;
        }
    }

    Type type;

    public LeftRightButton(ILeftRight screen, int xPos, int yPos, boolean isLeft, Type type) {
        super(xPos, yPos, xSize, ySize, isLeft ? 0 : 22, 0, 0, type.icon, (button) -> {
            int times = 1;
            if (type == Type.TEN) {
                times = 10;
            }
            for (int i = 0; i < times; i++) {
                if (isLeft) {
                    screen.goLeft();
                } else {
                    screen.goRight();
                }
            }
        });
        this.type = type;
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);
    }

}
