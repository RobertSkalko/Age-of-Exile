package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WikiTypeButton extends TexturedButtonWidget {

    public static int xSize = 20;
    public static int ySize = 20;

    static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/clear_button.png");

    Identifier icon;

    WikiType type;

    public WikiTypeButton(WikiScreen screen, WikiType type, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {
            screen.changeType(type);
        });
        this.type = type;
        this.icon = type.getIconLoc();

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);
        RenderUtils.render16Icon(matrix, icon, this.x + 2, this.y + 2);
    }

}

