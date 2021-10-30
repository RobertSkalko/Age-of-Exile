package com.robertx22.age_of_exile.gui.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.gui.overlays.bar_overlays.types.MineAndSlashBars;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ScalingDifficultyButton extends ImageButton {

    public static int BUTTON_SIZE_X = 110;
    public static int BUTTON_SIZE_Y = 15;

    Minecraft mc = Minecraft.getInstance();

    GuiPartConfig config = new GuiPartConfig(BarGuiType.SCALING_DIFFICULTY, new PointData(0, 0));

    public ScalingDifficultyButton(int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, new ResourceLocation("empty"), (button) -> {
        });
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<ITextComponent> tooltip = Load.playerRPGData(mc.player).scalingDifficulty.getTooltip();

            GuiUtils.renderTooltip(matrix,
                tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {

        MineAndSlashBars.renderMineAndSlashBar(config, BarGuiType.SCALING_DIFFICULTY,
            matrix,
            new PointData(this.x, this.y),
            config.type.getText(Load.Unit(mc.player), mc.player),
            false);

        MineAndSlashBars.renderMineAndSlashBar(config, BarGuiType.SCALING_DIFFICULTY,
            matrix,
            new PointData(this.x, this.y),
            config.type.getText(Load.Unit(mc.player), mc.player),
            true);

    }

}