package com.robertx22.age_of_exile.gui.screens.race_select;

import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RaceImageButton extends TexturedButtonWidget {

    public static int BUTTON_SIZE_X = 32;
    public static int BUTTON_SIZE_Y = 32;

    MinecraftClient mc = MinecraftClient.getInstance();
    PlayerRace race;

    public RaceImageButton(PlayerRace race, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, new Identifier("empty"), (button) -> {
        });
        this.race = race;

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            GuiUtils.renderTooltip(matrix,
                race.getTooltip(), x, y);
        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {
        mc.getTextureManager()
            .bindTexture(race.getIcon());
        drawTexture(matrix, this.x, this.y, 0, 0, this.width, this.height, width, height);

    }

}